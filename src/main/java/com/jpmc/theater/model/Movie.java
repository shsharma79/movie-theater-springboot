package com.jpmc.theater.model;

import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.time.Duration;
import java.util.Objects;

/**
 * Model for Movie
 *
 * <p>A Movie object has the following invariants:
 *
 * <ul>
 *   <li>Regular movies should not have a discount rate
 *   <li>Special movies should have a discount rate
 * </ul>
 *
 * @param id Id of the movie
 * @param title Title of the movie
 * @param runningTime Running time of the movie
 * @param ticketPrice Ticket price of the movie
 * @param isSpecial Indicates if the movie is a special movie that carries a discount
 * @param discountRate The discount rate if the movie is special
 */
public record Movie(
    long id,
    @NotNull String title,
    @NotNull Duration runningTime,
    @NotNull BigDecimal ticketPrice,
    boolean isSpecial,
    BigDecimal discountRate) {
  public static final String REGULAR_MOVIE_WITH_DISCOUNT_ERROR_MESSAGE =
      "Regular movie cannot have a discount";
  public static final String SPECIAL_MOVIE_WITH_ZERO_DISCOUNT_ERROR_MESSAGE =
      "Special movie should have a non-zero discount";

  public Movie {
    Objects.requireNonNull(title);
    Objects.requireNonNull(runningTime);
    Objects.requireNonNull(ticketPrice);
    if (!isSpecial && discountRate != null && discountRate.compareTo(BigDecimal.ZERO) > 0) {
      throw new IllegalArgumentException(REGULAR_MOVIE_WITH_DISCOUNT_ERROR_MESSAGE);
    }

    if (isSpecial && (discountRate == null || discountRate.compareTo(BigDecimal.ZERO) == 0)) {
      throw new IllegalArgumentException(SPECIAL_MOVIE_WITH_ZERO_DISCOUNT_ERROR_MESSAGE);
    }
  }
}
