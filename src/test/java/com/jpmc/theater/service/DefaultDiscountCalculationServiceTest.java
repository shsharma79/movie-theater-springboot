package com.jpmc.theater.service;

import com.jpmc.theater.model.Movie;
import com.jpmc.theater.model.Showing;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import java.math.BigDecimal;
import java.time.*;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@TestPropertySource(locations = "classpath:discount-calc-service-test.properties")
public class DefaultDiscountCalculationServiceTest {

  private final Movie regularMovie =
      new Movie(1, "Demo Movie", Duration.ofMinutes(90), BigDecimal.TEN, false, null);
  private final Movie specialMovie =
      new Movie(
          2, "Demo Movie", Duration.ofMinutes(90), BigDecimal.TEN, true, new BigDecimal("0.15"));
  @Autowired private DiscountCalculationService dcs;

  @Test
  public void whenNoDiscountApplicable_ShouldReturnZero() {
    Showing noDiscountShowing =
        new Showing(
            regularMovie,
            3,
            LocalDateTime.of(LocalDate.of(2022, Month.DECEMBER, 10), LocalTime.of(17, 0)));

    assertThat(dcs.calculateDiscountForShowing(noDiscountShowing)).isEqualTo(BigDecimal.ZERO);
  }

  @Test
  public void whenSpecialMovieDiscountIsMax_ShouldReturnSpecialMovieDiscount() {
    Showing showing =
        new Showing(
            specialMovie, // special movie with $1.5 discount
            7, // 7th showing has 1$ discount
            LocalDateTime.of(LocalDate.of(2022, Month.DECEMBER, 10), LocalTime.of(17, 0)));
    assertThat(dcs.calculateDiscountForShowing(showing))
        .isEqualByComparingTo(new BigDecimal("1.5"));
  }

  @Test
  public void whenShowingSequenceDiscountIsMax_ShouldReturnShowingDiscount() {
    Showing showing =
        new Showing(
            specialMovie, // special movie with $1.5 discount
            1, // 1st showing has 3$ discount
            LocalDateTime.of(LocalDate.of(2022, Month.DECEMBER, 10), LocalTime.of(17, 0)));
    assertThat(dcs.calculateDiscountForShowing(showing)).isEqualByComparingTo(new BigDecimal("3"));
  }

  @Test
  public void whenShowingStartTimeDiscountIsMax_ShouldReturnStartTimeDiscount() {
    Showing showing =
        new Showing(
            specialMovie, // special movie with $1.5 discount
            2, // 2nd showing has $2 discount
            LocalDateTime.of(
                LocalDate.of(2022, Month.DECEMBER, 10),
                LocalTime.of(15, 0))); // 11 AM - 4 PM discount is $2.5
    assertThat(dcs.calculateDiscountForShowing(showing))
        .isEqualByComparingTo(new BigDecimal("2.5"));
  }
}
