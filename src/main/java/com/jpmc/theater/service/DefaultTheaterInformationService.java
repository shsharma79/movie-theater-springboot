package com.jpmc.theater.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jpmc.theater.model.Theater;
import com.jpmc.theater.repository.SimpleTheaterRepository;
import com.jpmc.theater.util.LocalDateProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

import static com.jpmc.theater.config.ObjectMapperConfig.DATE_TIME_FORMATTER;

/** Default implementation of {@link TheaterInformationService} */
@Service
public class DefaultTheaterInformationService implements TheaterInformationService {

  public static final String DASH_LINE_SEPARATOR =
      "===================================================";
  public static final String THEATER_NOT_FOUND_MESSAGE = "Theater with id: %s not found.";
  @Autowired private SimpleTheaterRepository simpleTheaterRepository;
  @Autowired private LocalDateProvider provider;

  @Autowired private ObjectMapper mapper;

  @Override
  public void printTheaterSchedule(long theaterId, PrintFormat printFormat) {
    System.out.println(provider.currentDate());
    System.out.println(DASH_LINE_SEPARATOR);
    Optional<Theater> o = simpleTheaterRepository.getTheaterById(theaterId);
    if (o.isEmpty()) {
      System.out.printf((THEATER_NOT_FOUND_MESSAGE) + "%n", theaterId);
      System.out.println(DASH_LINE_SEPARATOR);
      return;
    }

    Theater t = o.get();
    switch (printFormat) {
      case PLAIN_TEXT -> printPlaintTextSchedule(t);
      case JSON -> printJsonTextSchedule(t);
      case JSON_AND_PLAINTEXT -> {
        printPlaintTextSchedule(t);
        printJsonTextSchedule(t);
      }
    }
    System.out.println(DASH_LINE_SEPARATOR);
  }

  private void printJsonTextSchedule(Theater t) {
    try {
      String jsonText = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(t.getSchedule());
      System.out.println(jsonText);
    } catch (JsonProcessingException e) {
      throw new RuntimeException(e);
    }
  }

  private void printPlaintTextSchedule(Theater t) {
    var schedule = t.getSchedule();
    schedule.forEach(
        s ->
            System.out.println(
                s.sequenceOfTheDay()
                    + ": "
                    + DATE_TIME_FORMATTER.format(s.showStartTime())
                    + " "
                    + s.movie().title()
                    + " "
                    + humanReadableFormat(s.movie().runningTime())
                    + " $"
                    + s.movie().ticketPrice()));
  }

  private String humanReadableFormat(Duration duration) {
    long hour = duration.toHours();
    long remainingMin = duration.toMinutes() - TimeUnit.HOURS.toMinutes(duration.toHours());

    return String.format(
        "(%s hour%s %s minute%s)",
        hour, handlePlural(hour), remainingMin, handlePlural(remainingMin));
  }

  // (s) postfix should be added to handle plural correctly
  private String handlePlural(long value) {
    if (value == 1) {
      return "";
    } else {
      return "s";
    }
  }
}
