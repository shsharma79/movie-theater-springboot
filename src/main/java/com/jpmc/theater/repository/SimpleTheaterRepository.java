package com.jpmc.theater.repository;

import com.jpmc.theater.model.Movie;
import com.jpmc.theater.model.Showing;
import com.jpmc.theater.model.Theater;
import com.jpmc.theater.util.LocalDateProvider;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;
import org.springframework.validation.annotation.Validated;

import java.math.BigDecimal;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.*;

/** A simple implementation where Theater object and all its dependencies are hard-coded. */
@Repository
@Validated
public class SimpleTheaterRepository implements TheaterRepository {

  private static final BigDecimal NO_DISCOUNT = BigDecimal.ZERO;
  @Autowired private LocalDateProvider provider;

  @Value("${movie.special.discount:0.20}")
  private BigDecimal specialMovieDiscount;

  private Map<Long, Theater> theaters;

  @PostConstruct
  private void init() {
    theaters = new HashMap<>();
    Theater t = new Theater(1, "Demo Theater", loadShowings());
    theaters.put(t.getTheaterId(), t);
  }

  private List<Showing> loadShowings() {
    Movie spiderMan =
        new Movie(
            1,
            "Spider-Man: No Way Home",
            Duration.ofMinutes(90),
            new BigDecimal("12.50"),
            true,
            specialMovieDiscount);
    Movie turningRed =
        new Movie(
            2, "Turning Red", Duration.ofMinutes(85), new BigDecimal("11.00"), false, NO_DISCOUNT);
    Movie theBatMan =
        new Movie(
            3, "The Batman", Duration.ofMinutes(95), new BigDecimal("9.00"), false, NO_DISCOUNT);

    return List.of(
        new Showing(turningRed, 1, LocalDateTime.of(provider.currentDate(), LocalTime.of(9, 0))),
        new Showing(spiderMan, 2, LocalDateTime.of(provider.currentDate(), LocalTime.of(11, 0))),
        new Showing(theBatMan, 3, LocalDateTime.of(provider.currentDate(), LocalTime.of(12, 50))),
        new Showing(turningRed, 4, LocalDateTime.of(provider.currentDate(), LocalTime.of(14, 30))),
        new Showing(spiderMan, 5, LocalDateTime.of(provider.currentDate(), LocalTime.of(16, 10))),
        new Showing(theBatMan, 6, LocalDateTime.of(provider.currentDate(), LocalTime.of(17, 50))),
        new Showing(turningRed, 7, LocalDateTime.of(provider.currentDate(), LocalTime.of(19, 30))),
        new Showing(spiderMan, 8, LocalDateTime.of(provider.currentDate(), LocalTime.of(21, 10))),
        new Showing(theBatMan, 9, LocalDateTime.of(provider.currentDate(), LocalTime.of(23, 0))));
  }

  public List<Theater> getTheaters() {
    return new ArrayList<>(theaters.values());
  }

  public Optional<Theater> getTheaterById(long id) {
    return Optional.ofNullable(theaters.get(id));
  }
}
