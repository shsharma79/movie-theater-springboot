package com.jpmc.theater.util;

import org.springframework.stereotype.Component;

import java.time.LocalDate;

/** Utility class that provides Current Date */
@Component
public class LocalDateProvider {

  /**
   * Returns the current date
   *
   * @return the current date
   */
  public LocalDate currentDate() {
    return LocalDate.now();
  }
}
