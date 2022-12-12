package com.jpmc.theater.service;

import com.jpmc.theater.model.Movie;
import com.jpmc.theater.model.Showing;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import java.math.BigDecimal;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.Month;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@TestPropertySource(locations = "classpath:app-test.properties")
public class DefaultReservationFeeCalculationServiceTest {
  private final Movie regularMovie =
      new Movie(1, "Demo Movie", Duration.ofMinutes(90), BigDecimal.TEN, false, null);
  private final Movie specialMovie =
      new Movie(
          2, "Demo Movie", Duration.ofMinutes(90), BigDecimal.TEN, true, new BigDecimal("0.15"));
  @Autowired private ReservationFeeCalculationService reservationFeeCalculationService;

  @Test
  public void whenNoDiscount_ShouldReturnTicketPrice() {
    Showing noDiscountShowing =
        new Showing(regularMovie, 3, LocalDateTime.of(2022, Month.DECEMBER, 11, 17, 0));
    assertThat(reservationFeeCalculationService.calculateTotalFeeForShowing(noDiscountShowing, 1))
        .isEqualByComparingTo(noDiscountShowing.movie().ticketPrice());
  }

  @Test
  void totalFee() {
    var showing =
        new Showing(
            new Movie(
                1,
                "Spider-Man: No Way Home",
                Duration.ofMinutes(90),
                new BigDecimal("12.5"),
                true,
                new BigDecimal("0.20")),
            1,
            LocalDateTime.of(2022, Month.DECEMBER, 11, 17, 0));
    var totalFee = reservationFeeCalculationService.calculateTotalFeeForShowing(showing, 3);
    assertThat(totalFee).isEqualByComparingTo(new BigDecimal("28.5"));
  }

  @Test
  void specialMovieWith50PercentDiscount() {
    Movie spiderMan =
        new Movie(
            1,
            "Spider-Man: No Way Home",
            Duration.ofMinutes(90),
            new BigDecimal("12.5"),
            true,
            new BigDecimal("0.50"));
    Showing showing = new Showing(spiderMan, 5, LocalDateTime.of(2022, Month.DECEMBER, 11, 17, 0));
    var totalFee = reservationFeeCalculationService.calculateTotalFeeForShowing(showing, 1);
    assertThat(totalFee).isEqualByComparingTo(new BigDecimal("6.25"));
  }
}
