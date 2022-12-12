package com.jpmc.theater.model;

import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;
import java.util.Objects;

/**
 * Model of Showing
 *
 * @param movie Movie that is part of this showing
 * @param sequenceOfTheDay Sequence of the day at which this showing is planned
 * @param showStartTime Start time of the showing
 */
public record Showing(
    @NotNull Movie movie, int sequenceOfTheDay, @NotNull LocalDateTime showStartTime) {
  public Showing {
    Objects.requireNonNull(movie);
    Objects.requireNonNull(showStartTime);
  }
}
