package com.jpmc.theater.util;

import jakarta.validation.constraints.NotNull;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

/**
 * Model for a Time range within a day
 *
 * @param start start time for the range (inclusive)
 * @param end end time for the range (inclusive)
 */
public record TimeRange(@NotNull LocalTime start, LocalTime end) {
  public static final String INCORRECT_RANGE_FORMAT = "Incorrect TimeRange Format";
  private static final String TIME_RANGE_PATTERN = "HH[:mm]";
  private static final DateTimeFormatter FORMATTER =
      DateTimeFormatter.ofPattern(TIME_RANGE_PATTERN);

  public TimeRange {
    Objects.requireNonNull(start);
  }

  /**
   * Returns a TimeRange for the input string
   *
   * <p>Valid format for the input string is HH[[:mm]-HH[:mm]]
   *
   * <p>end time is optional. The minutes in start and end times are optional as well.
   *
   * <p>Valid examples: 10, 10-12, 10:30, 10:30-12, 10:30-12:30
   *
   * @param str input string
   * @return TimeRange
   */
  public static TimeRange of(String str) {
    String[] values = str.split("-");
    switch (values.length) {
      case 1:
        return new TimeRange(LocalTime.parse(values[0], FORMATTER), null);
      case 2:
        return new TimeRange(
            LocalTime.parse(values[0], FORMATTER), LocalTime.parse(values[1], FORMATTER));
      default:
        throw new IllegalArgumentException(INCORRECT_RANGE_FORMAT);
    }
  }

  /**
   * Returns true if the inputTime is within start and end of the TimeRange
   *
   * <p>Both start and end are treated as inclusive in the comparison
   *
   * @param inputTime input time
   * @return true if the inputTime is within start and end of the TimeRange, false otherwise
   */
  public boolean isInTheRange(LocalTime inputTime) {
    if (end == null) {
      return start.equals(inputTime);
    }
    return start.equals(inputTime)
        || (start.isBefore(inputTime) && end.isAfter(inputTime))
        || end.equals(inputTime);
  }
}
