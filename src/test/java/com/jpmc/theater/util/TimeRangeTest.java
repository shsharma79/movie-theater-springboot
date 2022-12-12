package com.jpmc.theater.util;

import org.junit.jupiter.api.Test;

import java.time.LocalTime;
import java.time.format.DateTimeParseException;

import static org.assertj.core.api.Assertions.*;

public class TimeRangeTest {

  @Test
  public void whenNoEndTime_ShouldReturnTrueIfEqualsStartTime() {
    TimeRange range = new TimeRange(LocalTime.of(15, 0), null);
    var otherTime = LocalTime.of(15, 0);
    assertThat(range.isInTheRange(otherTime)).isTrue();
  }

  @Test
  public void whenNoEndTime_ShouldReturnFalseIfDoesNotEqualsStartTime() {
    TimeRange range = new TimeRange(LocalTime.of(15, 0), null);
    var otherTime = LocalTime.of(15, 1);
    assertThat(range.isInTheRange(otherTime)).isFalse();
  }

  @Test
  public void whenOtherTimeEqualToStart_ShouldReturnTrue() {
    TimeRange range = new TimeRange(LocalTime.of(15, 0), LocalTime.of(16, 0));
    var otherTime = LocalTime.of(15, 0);
    assertThat(range.isInTheRange(otherTime)).isTrue();
  }

  @Test
  public void whenOtherTimeEqualToEnd_ShouldReturnTrue() {
    TimeRange range = new TimeRange(LocalTime.of(15, 0), LocalTime.of(16, 0));
    var otherTime = LocalTime.of(16, 0);
    assertThat(range.isInTheRange(otherTime)).isTrue();
  }

  @Test
  public void whenOtherTimeInRange_ShouldReturnTrue() {
    TimeRange range = new TimeRange(LocalTime.of(15, 0), LocalTime.of(16, 0));
    var otherTime = LocalTime.of(15, 45);
    assertThat(range.isInTheRange(otherTime)).isTrue();
  }

  @Test
  public void whenOtherTimeOutOfRange_ShouldReturnFalse() {
    TimeRange range = new TimeRange(LocalTime.of(15, 0), LocalTime.of(16, 0));
    var otherTime = LocalTime.of(17, 45);
    assertThat(range.isInTheRange(otherTime)).isFalse();
  }

  @Test
  public void whenValidString_ShouldReturnTimeRange() {
    TimeRange range = TimeRange.of("11-16:30");
    assertThat(range).isNotNull();
    assertThat(range.start()).isNotNull();
    assertThat(range.start().getHour()).isEqualTo(11);
    assertThat(range.start().getMinute()).isEqualTo(0);
    assertThat(range.end()).isNotNull();
    assertThat(range.end().getHour()).isEqualTo(16);
    assertThat(range.end().getMinute()).isEqualTo(30);
  }

  @Test
  public void whenMoreThanTwoValuesPassed_ShouldThrowException() {
    assertThatIllegalArgumentException()
        .isThrownBy(
            () -> {
              TimeRange.of("11-16:30-12");
            })
        .withMessage(TimeRange.INCORRECT_RANGE_FORMAT);
  }

  @Test
  public void whenInvalidFormatStringPassed_ShouldThrowException() {
    assertThatExceptionOfType(DateTimeParseException.class)
        .isThrownBy(
            () -> {
              TimeRange.of("AB-CD");
            });
  }
}
