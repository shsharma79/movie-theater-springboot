package com.jpmc.theater.discountrule;

import com.jpmc.theater.model.Showing;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

import java.math.BigDecimal;
import java.util.Map;

/**
 * Discount rule that calculates the discount using sequence of the day for the showing as the
 * criteria
 */
@Component
@Validated
public class ShowingSequenceDiscountRule implements ShowingDiscountRule {
  @Value("#{${showing.sequence.to.discount.map}}")
  private Map<Integer, BigDecimal> showingSequenceToDiscountMap;

  @Override
  public BigDecimal calculateDiscount(@NotNull Showing input) {
    BigDecimal discount = BigDecimal.ZERO;
    if (showingSequenceToDiscountMap.containsKey(input.sequenceOfTheDay())) {
      discount = showingSequenceToDiscountMap.get(input.sequenceOfTheDay());
    }
    return discount;
  }
}
