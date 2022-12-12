package com.jpmc.theater.service;

import com.jpmc.theater.model.Showing;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.math.BigDecimal;

import static com.jpmc.theater.config.AppConfig.moneyRoundingMode;
import static com.jpmc.theater.config.AppConfig.moneyScale;

/** Default implementation of {@link ReservationFeeCalculationService} */
@Service
@Validated
public class DefaultReservationFeeCalculationService implements ReservationFeeCalculationService {

  @Autowired private DiscountCalculationService discountCalculationService;

  @Override
  public BigDecimal calculateTotalFeeForShowing(@NotNull Showing showing, int audienceCount) {
    // reservation fee = movie price - discount
    var discount = discountCalculationService.calculateDiscountForShowing(showing);
    return showing
        .movie()
        .ticketPrice()
        .subtract(discount)
        .multiply(new BigDecimal(audienceCount))
        .setScale(moneyScale(), moneyRoundingMode());
  }
}
