package com.jpmc.theater.model;

import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.util.Objects;

import static com.jpmc.theater.config.ObjectMapperConfig.DATE_TIME_FORMATTER;

/**
 * Model for Reservation
 *
 * @param theater Theater for the reservation
 * @param customer Customer of the reservation
 * @param showing The movie showing for the reservation
 * @param audienceCount Number of people on the reservation
 * @param totalFee Total amount for the reservation
 */
public record Reservation(
    @NotNull Theater theater,
    @NotNull Customer customer,
    @NotNull Showing showing,
    int audienceCount,
    @NotNull BigDecimal totalFee) {
  public Reservation {
    Objects.requireNonNull(theater);
    Objects.requireNonNull(customer);
    Objects.requireNonNull(showing);
    Objects.requireNonNull(totalFee);
  }

  /**
   * Provides a human-readable summary for the Reservation
   *
   * @return Human-readable summary of the reservation
   */
  public String summary() {
    return "Customer Name: "
        + customer.name()
        + "\n"
        + "Theater Name: "
        + theater.getTheaterName()
        + "\n"
        + "Movie Name: "
        + showing.movie().title()
        + "\n"
        + "Start Time: "
        + DATE_TIME_FORMATTER.format(showing.showStartTime())
        + "\n"
        + "Number of tickets: "
        + audienceCount
        + "\n"
        + "Total Fee: "
        + totalFee;
  }
}
