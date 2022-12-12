package com.jpmc.theater.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

public class TheaterTest {

  private Theater theater;

  @BeforeEach
  public void setup() {
    Movie movie = new Movie(1, "Demo Movie", Duration.ofMinutes(90), BigDecimal.TEN, false, null);
    Showing showing = new Showing(movie, 1, LocalDateTime.of(2022, Month.DECEMBER, 10, 10, 0));
    theater = new Theater(1, "Demo Theater", List.of(showing));
  }

  @Test
  public void whenValidSequence_ShouldReturnMatchingShowing() {
    Optional<Showing> showingOptional = theater.getShowingBySequence(1);
    assertThat(showingOptional).isPresent();
  }

  @Test
  public void whenInValidSequence_ShouldReturnEmptyOptional() {
    Optional<Showing> showingOptional = theater.getShowingBySequence(10);
    assertThat(showingOptional).isEmpty();
  }
}
