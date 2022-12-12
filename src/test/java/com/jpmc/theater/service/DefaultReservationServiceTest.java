package com.jpmc.theater.service;

import com.jpmc.theater.exception.ReservationException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;

import static com.jpmc.theater.exception.ReservationException.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

@SpringBootTest
public class DefaultReservationServiceTest {

  private final String customerId = "jdoe";
  private final long theaterId = 1L;
  @Autowired private DefaultReservationService defaultReservationService;

  @Test
  public void whenInvalidCustomerId_ShouldThrowReservationException() {
    assertThatExceptionOfType(ReservationException.class)
        .isThrownBy(
            () -> {
              defaultReservationService.reserve("invalidCustomerId", 1, 2, theaterId);
            })
        .withMessage(CUSTOMER_NOT_FOUND);
  }

  @Test
  public void whenInvalidTheaterId_ShouldThrowReservationException() {
    assertThatExceptionOfType(ReservationException.class)
        .isThrownBy(
            () -> {
              defaultReservationService.reserve(customerId, 1, 2, 10);
            })
        .withMessage(THEATER_NOT_FOUND);
  }

  @Test
  public void whenInvalidShowingSequence_ShouldThrowReservationException() {
    assertThatExceptionOfType(ReservationException.class)
        .isThrownBy(
            () -> {
              defaultReservationService.reserve(customerId, 100, 2, theaterId);
            })
        .withMessage(SHOWING_NOT_FOUND);
  }

  @Test
  public void whenValidInput_ShouldCompleteTheReservation() {
    var reservation = defaultReservationService.reserve(customerId, 1, 2, theaterId);
    assertThat(reservation).isNotNull();
    assertThat(reservation.customer().id()).isEqualTo(customerId);
    assertThat(reservation.theater().getTheaterId()).isEqualTo(1L);
    assertThat(reservation.showing().sequenceOfTheDay()).isEqualTo(1);
    assertThat(reservation.audienceCount()).isEqualTo(2);
    // Max discount = Sequence discount = $3
    // Ticket Price = 11 - 3 = $8
    // Total fee = 2 * 8 = $16
    assertThat(reservation.totalFee()).isEqualByComparingTo(new BigDecimal("16"));
  }

  @Test
  void totalFeeForCustomer() {
    var reservation = defaultReservationService.reserve(customerId, 2, 4, theaterId);
    assertThat(reservation.totalFee()).isEqualByComparingTo(new BigDecimal("37.5"));
  }
}
