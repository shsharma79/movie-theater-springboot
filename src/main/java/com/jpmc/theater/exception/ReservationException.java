package com.jpmc.theater.exception;

/** Exception class for Reservation Service exceptions */
public class ReservationException extends RuntimeException {
  public static final String THEATER_NOT_FOUND = "No theater found for the given theater id";
  public static final String SHOWING_NOT_FOUND = "No showing found for the given sequence id";
  public static final String CUSTOMER_NOT_FOUND = "No customer found for the given customerId";

  public ReservationException(String message) {
    super(message);
  }
}
