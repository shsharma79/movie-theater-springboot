package com.jpmc.theater.service;

import com.jpmc.theater.model.Showing;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

/**
 * Interface that defines contract for calculation of discount using discount rules @See
 * ShowingDiscountRule
 */
public interface DiscountCalculationService {

  /**
   * Returns the total discount for the showing or ZERO if no discount applies
   *
   * <p>If multiple discounts are applicable for the showing, maximum discount amount is returned
   *
   * @param showing the input showing
   * @return maximum total discount for the showing or ZERO if no discount is applicable
   */
  BigDecimal calculateDiscountForShowing(@NotNull Showing showing);
}
