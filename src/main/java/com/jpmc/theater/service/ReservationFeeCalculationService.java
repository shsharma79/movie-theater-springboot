package com.jpmc.theater.service;

import com.jpmc.theater.model.Showing;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

/** Interface that defines the contract for calculation of total fee for the reservation */
public interface ReservationFeeCalculationService {

  /**
   * Returns total fee (amount) for the showing
   *
   * @param showing the input showing
   * @param audienceCount Number of people
   * @return Total fee (amount) for the showing
   */
  BigDecimal calculateTotalFeeForShowing(@NotNull Showing showing, int audienceCount);
}
