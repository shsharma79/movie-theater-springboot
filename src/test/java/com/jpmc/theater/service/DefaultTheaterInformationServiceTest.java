package com.jpmc.theater.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jpmc.theater.model.Showing;
import com.jpmc.theater.repository.SimpleTheaterRepository;
import com.jpmc.theater.util.LocalDateProvider;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import uk.org.webcompere.systemstubs.jupiter.SystemStub;
import uk.org.webcompere.systemstubs.jupiter.SystemStubsExtension;
import uk.org.webcompere.systemstubs.stream.SystemOut;

import java.util.List;
import java.util.function.Consumer;

import static com.jpmc.theater.service.DefaultTheaterInformationService.DASH_LINE_SEPARATOR;
import static com.jpmc.theater.service.DefaultTheaterInformationService.THEATER_NOT_FOUND_MESSAGE;
import static com.jpmc.theater.service.TheaterInformationService.PrintFormat.JSON;
import static com.jpmc.theater.service.TheaterInformationService.PrintFormat.PLAIN_TEXT;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@ExtendWith(SystemStubsExtension.class)
public class DefaultTheaterInformationServiceTest {

  @Autowired private TheaterInformationService theaterInformationService;

  @Autowired private LocalDateProvider localDateProvider;

  @Autowired private SimpleTheaterRepository simpleTheaterRepository;

  @Autowired private ObjectMapper mapper;

  @SystemStub private SystemOut systemOut;

  @Test
  public void whenFormatPlainText_ShouldPrintTextSchedule() {
    var validTheaterId = 1;
    theaterInformationService.printTheaterSchedule(validTheaterId, PLAIN_TEXT);
    Consumer<List<String>> specificValidation =
        s -> {
          assertThat(s.subList(2, s.size() - 1).size())
              .isEqualTo(
                  simpleTheaterRepository
                      .getTheaterById(validTheaterId)
                      .get()
                      .getSchedule()
                      .size());
        };
    validatePrintScheduleOutput(systemOut.getLines().toList(), specificValidation);
  }

  @Test
  public void whenFormatJson_ShouldPrintJsonTextSchedule() {
    var validTheaterId = 1;
    theaterInformationService.printTheaterSchedule(validTheaterId, JSON);
    Consumer<List<String>> specificValidation =
        s -> {
          // Removed header and footer and Build a list of Showing objects
          StringBuilder sb = new StringBuilder();
          s.subList(2, s.size() - 1).stream()
              .forEach(
                  line -> {
                    sb.append(line);
                  });
          try {
            List<Showing> showings =
                mapper.readValue(sb.toString(), new TypeReference<List<Showing>>() {});
            assertThat(showings.size())
                .isEqualTo(
                    simpleTheaterRepository
                        .getTheaterById(validTheaterId)
                        .get()
                        .getSchedule()
                        .size());
          } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
          }
        };
    validatePrintScheduleOutput(systemOut.getLines().toList(), specificValidation);
  }

  @Test
  public void whenInvalidTheaterId_ShouldPrintErrorMessage() {
    var invalidTheaterId = -1;
    theaterInformationService.printTheaterSchedule(
        invalidTheaterId, TheaterInformationService.PrintFormat.PLAIN_TEXT);
    Consumer<List<String>> specificValidation =
        s -> {
          assertThat(s.get(2))
              .isEqualTo(String.format(THEATER_NOT_FOUND_MESSAGE, invalidTheaterId));
        };
    validatePrintScheduleOutput(systemOut.getLines().toList(), specificValidation);
  }

  private void validatePrintScheduleOutput(
      List<String> outputLines, Consumer<List<String>> specificValidation) {
    testPrintScheduleStart(outputLines);
    specificValidation.accept(outputLines);
    testPrintScheduleEnd(outputLines);
  }

  private void testPrintScheduleStart(List<String> logLines) {
    assertThat(logLines.get(0)).isEqualTo(localDateProvider.currentDate().toString());
    assertThat(logLines.get(1)).isEqualTo(DASH_LINE_SEPARATOR);
  }

  private void testPrintScheduleEnd(List<String> logLines) {
    var lastLogLine = logLines.get(logLines.size() - 1);
    assertThat(lastLogLine).isEqualTo(DASH_LINE_SEPARATOR);
  }
}
