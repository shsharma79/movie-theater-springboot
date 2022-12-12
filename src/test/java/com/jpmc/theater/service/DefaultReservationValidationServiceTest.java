package com.jpmc.theater.service;

import com.jpmc.theater.model.Customer;
import com.jpmc.theater.model.Movie;
import com.jpmc.theater.model.Showing;
import com.jpmc.theater.model.Theater;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.List;
import java.util.Optional;

import static com.jpmc.theater.exception.ReservationException.*;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class DefaultReservationValidationServiceTest {

  private final Movie movie =
      new Movie(1, "Demo Movie", Duration.ofMinutes(90), BigDecimal.TEN, false, null);
  private final Showing showing =
      new Showing(movie, 1, LocalDateTime.of(2022, Month.DECEMBER, 10, 13, 0));
  private final Theater theater = new Theater(1, "Demo Theater", List.of(showing));
  private final Customer dummyCustomer = new Customer("John Doe", "jdoe");
  @Autowired private ReservationValidationService reservationValidationService;

  @Test
  public void whenValidValues_ShouldReturnValidResponse() {
    int validSequence = 1;
    var response =
        reservationValidationService.validateReservation(
            Optional.ofNullable(dummyCustomer), Optional.ofNullable(theater), validSequence, 2);
    assertThat(response.valid()).isTrue();
    assertThat(response.message()).isEmpty();
  }

  @Test
  public void whenInvalidCustomer_ShouldReturnErrorResponse() {
    var response =
        reservationValidationService.validateReservation(
            Optional.ofNullable(null), Optional.ofNullable(theater), 1, 2);
    assertThat(response.valid()).isFalse();
    assertThat(response.message()).isEqualTo(CUSTOMER_NOT_FOUND);
  }

  @Test
  public void whenInvalidTheater_ShouldReturnErrorResponse() {
    var response =
        reservationValidationService.validateReservation(
            Optional.ofNullable(dummyCustomer), Optional.ofNullable(null), 1, 2);
    assertThat(response.valid()).isFalse();
    assertThat(response.message()).isEqualTo(THEATER_NOT_FOUND);
  }

  @Test
  public void whenInvalidSequence_ShouldReturnErrorResponse() {
    int invalidSequence = 10;
    var response =
        reservationValidationService.validateReservation(
            Optional.ofNullable(dummyCustomer), Optional.ofNullable(theater), invalidSequence, 2);
    assertThat(response.valid()).isFalse();
    assertThat(response.message()).isEqualTo(SHOWING_NOT_FOUND);
  }
}
