package com.jpmc.theater.model;

import java.util.*;

/** Model of Theater */
public class Theater {

  private final long theaterId;
  private final String theaterName;
  private final List<Showing> schedule;
  private final Map<Integer, Showing> sequenceToShowing;

  public Theater(long theaterId, String theaterName, List<Showing> schedule) {
    this.theaterId = theaterId;
    this.theaterName = theaterName;
    this.schedule = Collections.unmodifiableList(schedule);
    sequenceToShowing = new HashMap<>(schedule.size());
    schedule.stream()
        .forEach(
            s -> {
              sequenceToShowing.put(s.sequenceOfTheDay(), s);
            });
  }

  public long getTheaterId() {
    return theaterId;
  }

  public String getTheaterName() {
    return theaterName;
  }

  public List<Showing> getSchedule() {
    return schedule;
  }

  /**
   * Returns the showing for the provided sequence or an empty Optional if there are no showings for
   * the input sequence
   *
   * @param sequenceOfTheDay sequence of the day for which Showing needs to be returned
   * @return Showing for the input sequence, or empty Optional if no showings found
   */
  public Optional<Showing> getShowingBySequence(int sequenceOfTheDay) {
    return Optional.ofNullable(sequenceToShowing.get(sequenceOfTheDay));
  }
}
