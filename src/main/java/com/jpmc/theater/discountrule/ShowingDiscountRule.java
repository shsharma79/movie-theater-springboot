package com.jpmc.theater.discountrule;

import com.jpmc.theater.model.Showing;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

/**
 * Interface for discount rules for {@link Showing}s
 *
 * <p>Each rule can have one or more criteria
 */
public interface ShowingDiscountRule {
  /**
   * Calculates the discount amount for the passed in {@link Showing}
   *
   * <p>If the showing matches multiple discount criteria within the same rule, the criteria with
   * maximum discount should be selected and the corresponding discount should be returned.
   *
   * <p>If the showing does not match rule criteria, it should return {@link BigDecimal} ZERO
   *
   * @param showing the showing for which discount needs to be calculated
   * @return the maximum discount amount as per the rule or Zero if showing doesn't meet rule
   *     criteria
   */
  BigDecimal calculateDiscount(@NotNull Showing showing);
}
