package com.jpmc.theater.discountrule;

import com.jpmc.theater.model.Movie;
import com.jpmc.theater.model.Showing;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.time.Duration;
import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class ShowingSpecialMovieDiscountRuleTest {
  @Autowired private ShowingDiscountRule showingSpecialMovieDiscountRule;

  @Test
  public void whenSpecialMovie_ReturnValidDiscount() {
    Movie specialMovie =
        new Movie(
            1,
            "Demo Movie",
            Duration.ofMinutes(90),
            new BigDecimal("10.00"),
            true,
            new BigDecimal("0.20"));
    Showing showing = new Showing(specialMovie, 1, LocalDateTime.now());
    assertThat(showingSpecialMovieDiscountRule.calculateDiscount(showing))
        .isEqualByComparingTo(new BigDecimal("2"));
  }

  @Test
  public void whenRegularMovie_ShouldReturnZeroDiscount() {
    Movie regularMovie =
        new Movie(
            1,
            "Demo Movie",
            Duration.ofMinutes(90),
            new BigDecimal("10.00"),
            false,
            BigDecimal.ZERO);
    Showing showing = new Showing(regularMovie, 1, LocalDateTime.now());
    assertThat(showingSpecialMovieDiscountRule.calculateDiscount(showing))
        .isEqualByComparingTo(BigDecimal.ZERO);
  }
}
