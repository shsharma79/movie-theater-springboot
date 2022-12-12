package com.jpmc.theater.model;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.Duration;

import static com.jpmc.theater.model.Movie.REGULAR_MOVIE_WITH_DISCOUNT_ERROR_MESSAGE;
import static com.jpmc.theater.model.Movie.SPECIAL_MOVIE_WITH_ZERO_DISCOUNT_ERROR_MESSAGE;
import static org.assertj.core.api.Assertions.*;

public class MovieTest {

  @Test
  public void whenRegularMovieWithDiscount_ShouldThrowException() {
    assertThatIllegalArgumentException()
        .isThrownBy(
            () -> {
              new Movie(
                  1,
                  "Demo Movie",
                  Duration.ofMinutes(90),
                  new BigDecimal("10.20"),
                  false,
                  new BigDecimal("0.20"));
            })
        .withMessage(REGULAR_MOVIE_WITH_DISCOUNT_ERROR_MESSAGE);
  }

  @Test
  public void whenRegularMovieWithZeroDiscount_ShouldNotThrowException() {
    Movie regularMovie =
        new Movie(
            1,
            "Demo Movie",
            Duration.ofMinutes(90),
            new BigDecimal("10.20"),
            false,
            BigDecimal.ZERO);
    assertThat(regularMovie).isNotNull();
    assertThat(regularMovie.isSpecial()).isFalse();
    assertThat(regularMovie.discountRate()).isEqualTo(BigDecimal.ZERO);
  }

  @Test
  public void whenRegularMovieWithNullDiscount_ShouldNotThrowException() {
    Movie regularMovie =
        new Movie(1, "Demo Movie", Duration.ofMinutes(90), new BigDecimal("10.20"), false, null);
    assertThat(regularMovie).isNotNull();
    assertThat(regularMovie.isSpecial()).isFalse();
    assertThat(regularMovie.discountRate()).isNull();
  }

  @Test
  public void whenSpecialMovieWithZeroDiscount_ShouldThrowException() {
    assertThatIllegalArgumentException()
        .isThrownBy(
            () -> {
              new Movie(
                  1,
                  "Demo Movie",
                  Duration.ofMinutes(90),
                  new BigDecimal("10.20"),
                  true,
                  BigDecimal.ZERO);
            })
        .withMessage(SPECIAL_MOVIE_WITH_ZERO_DISCOUNT_ERROR_MESSAGE);
  }

  @Test
  public void whenSpecialMovieWithNullDiscount_ShouldThrowException() {
    assertThatIllegalArgumentException()
        .isThrownBy(
            () -> {
              new Movie(
                  1, "Demo Movie", Duration.ofMinutes(90), new BigDecimal("10.20"), true, null);
            })
        .withMessage(SPECIAL_MOVIE_WITH_ZERO_DISCOUNT_ERROR_MESSAGE);
  }

  @Test
  public void whenSpecialMovieWithNonZeroDiscount_ShouldNotThrowException() {
    Movie specialMovie =
        new Movie(
            1,
            "Demo Movie",
            Duration.ofMinutes(90),
            new BigDecimal("10.20"),
            true,
            new BigDecimal("0.20"));
    assertThat(specialMovie).isNotNull();
    assertThat(specialMovie.isSpecial()).isTrue();
    assertThat(specialMovie.discountRate()).isEqualTo(new BigDecimal("0.20"));
  }

  @Test
  public void whenMovieWithNullTicketPrice_ShouldThrowException() {
    assertThatNullPointerException()
        .isThrownBy(
            () -> {
              new Movie(
                  1, "Demo Movie", Duration.ofMinutes(90), null, true, new BigDecimal("0.20"));
            });
  }

  @Test
  public void whenMovieWithNullDuration_ShouldThrowException() {
    assertThatNullPointerException()
        .isThrownBy(
            () -> {
              new Movie(
                  1, "Demo Movie", null, new BigDecimal("10.20"), true, new BigDecimal("0.20"));
            });
  }
}
