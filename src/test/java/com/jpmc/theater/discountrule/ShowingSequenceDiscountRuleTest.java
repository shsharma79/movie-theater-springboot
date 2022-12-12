package com.jpmc.theater.discountrule;

import com.jpmc.theater.model.Movie;
import com.jpmc.theater.model.Showing;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import java.math.BigDecimal;
import java.time.Duration;
import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@TestPropertySource(locations = "classpath:app-test.properties")
public class ShowingSequenceDiscountRuleTest {

  private final Movie movie =
      new Movie(1, "Demo Movie", Duration.ofMinutes(90), BigDecimal.TEN, false, null);
  @Autowired private ShowingDiscountRule showingSequenceDiscountRule;

  @Test
  public void whenMatchingShowingSequenceIsFound_ShouldReturnDiscount() {
    Showing showing = new Showing(movie, 1, LocalDateTime.now());
    assertThat(showingSequenceDiscountRule.calculateDiscount(showing))
        .isEqualByComparingTo(new BigDecimal("3.00"));
  }

  @Test
  public void whenMatchingShowingSequenceNotFound_ShouldReturnZeroDiscount() {
    Showing showing = new Showing(movie, 3, LocalDateTime.now());
    assertThat(showingSequenceDiscountRule.calculateDiscount(showing)).isEqualTo(BigDecimal.ZERO);
  }
}
