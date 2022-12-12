package com.jpmc.theater.service;

/** Interface that defines contract for displaying/returning theater information */
public interface TheaterInformationService {

  /**
   * Prints the theater schedule on System Out in the specified format
   *
   * @param theaterId id of the theater
   * @param printFormat format for the schedule
   */
  void printTheaterSchedule(long theaterId, PrintFormat printFormat);

  /** Supported formats for theater schedule display */
  enum PrintFormat {
    PLAIN_TEXT,
    JSON,
    JSON_AND_PLAINTEXT
  }
}
