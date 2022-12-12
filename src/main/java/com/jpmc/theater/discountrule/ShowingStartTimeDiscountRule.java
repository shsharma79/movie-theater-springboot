package com.jpmc.theater.discountrule;

import com.jpmc.theater.model.Showing;
import com.jpmc.theater.util.TimeRange;
import jakarta.annotation.PostConstruct;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

/** Discount rule that calculates the discount using showing start time as the criteria */
@Component
@Validated
public class ShowingStartTimeDiscountRule implements ShowingDiscountRule {

  @Value("#{${showing.time.range.to.discount.map}}")
  private Map<String, BigDecimal> injectedRangeToDiscountRateMap;

  private Map<TimeRange, BigDecimal> timeRangeToDiscountRateMap;

  @PostConstruct
  public void init() {
    timeRangeToDiscountRateMap = new HashMap<>(injectedRangeToDiscountRateMap.size());
    injectedRangeToDiscountRateMap.entrySet().stream()
        .forEach(
            e -> {
              timeRangeToDiscountRateMap.put(TimeRange.of(e.getKey()), e.getValue());
            });
  }

  @Override
  public BigDecimal calculateDiscount(@NotNull Showing showing) {
    var discount = BigDecimal.ZERO;
    for (var e : timeRangeToDiscountRateMap.entrySet()) {
      if (e.getKey().isInTheRange(showing.showStartTime().toLocalTime())) {
        var showingDiscount = showing.movie().ticketPrice().multiply(e.getValue());
        discount = showingDiscount.compareTo(discount) > 0 ? showingDiscount : discount;
      }
    }
    return discount;
  }
}
