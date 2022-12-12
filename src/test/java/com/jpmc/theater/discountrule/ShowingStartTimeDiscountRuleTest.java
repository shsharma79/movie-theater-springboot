package com.jpmc.theater.discountrule;

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
@TestPropertySource(locations = "classpath:app-test.properties")
public class ShowingStartTimeDiscountRuleTest {

  private final Movie movie =
      new Movie(1, "Demo Movie", Duration.ofMinutes(90), BigDecimal.TEN, false, null);
  @Autowired private ShowingDiscountRule showingStartTimeDiscountRule;

  @Test
  public void whenStartTimeOutOfRange_shouldReturnZeroDiscount() {
    Showing showing =
        new Showing(
            movie,
            7,
            LocalDateTime.of(LocalDate.of(2022, Month.DECEMBER, 10), LocalTime.of(17, 0)));
    assertThat(showingStartTimeDiscountRule.calculateDiscount(showing))
        .isEqualByComparingTo(BigDecimal.ZERO);
  }

  @Test
  public void whenStartTimeInRange_shouldReturnCorrectDiscount() {
    Showing showing =
        new Showing(
            movie,
            7,
            LocalDateTime.of(LocalDate.of(2022, Month.DECEMBER, 10), LocalTime.of(15, 0)));
    assertThat(showingStartTimeDiscountRule.calculateDiscount(showing))
        .isEqualByComparingTo(new BigDecimal("2.50"));
  }

  @Test
  public void whenStartTimeInMultipleRanges_shouldReturnMaxDiscount() {
    Showing showing =
        new Showing(
            movie,
            7,
            LocalDateTime.of(LocalDate.of(2022, Month.DECEMBER, 10), LocalTime.of(12, 30)));
    assertThat(showingStartTimeDiscountRule.calculateDiscount(showing))
        .isEqualByComparingTo(new BigDecimal("5.50"));
  }
}
