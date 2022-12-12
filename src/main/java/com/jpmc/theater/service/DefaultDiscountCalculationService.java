package com.jpmc.theater.service;

import com.jpmc.theater.discountrule.ShowingDiscountRule;
import com.jpmc.theater.model.Showing;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.math.BigDecimal;
import java.util.Comparator;
import java.util.List;

/** Default implementation of DiscountCalculationService */
@Service
@Validated
public class DefaultDiscountCalculationService implements DiscountCalculationService {

  @Autowired private List<ShowingDiscountRule> showingDiscountRules;

  @Override
  public BigDecimal calculateDiscountForShowing(@NotNull Showing showing) {
    return showingDiscountRules.stream()
        .map(r -> r.calculateDiscount(showing))
        .max(Comparator.naturalOrder())
        .orElse(BigDecimal.ZERO);
  }
}
