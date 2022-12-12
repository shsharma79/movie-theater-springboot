package com.jpmc.theater.service;

import com.jpmc.theater.model.Reservation;
import jakarta.validation.constraints.NotNull;

/** Interface that defines the contract for booking a reservation */
public interface ReservationService {
  /**
   * Returns a booked reservation with total fee (amount)
   *
   * <p>If the reservation can't be booked, throws {@link
   * com.jpmc.theater.exception.ReservationException}
   *
   * @param customerId id of the Customer
   * @param sequence sequence of the day
   * @param howManyTickets number of people
   * @param theaterId id of the Theater
   * @return Reservation with total fee (amount)
   */
  Reservation reserve(@NotNull String customerId, int sequence, int howManyTickets, long theaterId);
}
