package com.jpmc.theater.discountrule;

import com.jpmc.theater.model.Showing;
import jakarta.validation.constraints.NotNull;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

import java.math.BigDecimal;

/** Discount rule that calculates the discount using special status of the movie as the criteria */
@Component
@Validated
public class ShowingSpecialMovieDiscountRule implements ShowingDiscountRule {

  @Override
  public BigDecimal calculateDiscount(@NotNull Showing showing) {
    if (showing.movie().isSpecial()) {
      var movie = showing.movie();
      return movie.ticketPrice().multiply(movie.discountRate());
    }
    return BigDecimal.ZERO;
  }
}
