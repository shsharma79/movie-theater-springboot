package com.jpmc.theater.repository;

import com.jpmc.theater.model.Theater;

import java.util.List;
import java.util.Optional;

/** Interface that allows access to Theaters setup in the application */
public interface TheaterRepository {

  /**
   * Returns the list of all Theaters
   *
   * @return list of all Theaters
   */
  List<Theater> getTheaters();

  /**
   * Returns an Optional with Theater for the given theater id or an empty Optional if no theater
   * found for the input id
   *
   * @param theaterId theater id
   * @return Optional with Theater for the given theater id or an empty Optional if no theater found
   *     for the input id
   */
  Optional<Theater> getTheaterById(long theaterId);
}
