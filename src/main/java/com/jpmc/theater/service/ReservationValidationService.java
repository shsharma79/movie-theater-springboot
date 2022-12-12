package com.jpmc.theater.service;

import com.jpmc.theater.model.Customer;
import com.jpmc.theater.model.Theater;
import com.jpmc.theater.util.ValidationResponse;
import jakarta.validation.constraints.NotNull;

import java.util.Optional;

/** Interface that defines the contract for validation of reservation request */
public interface ReservationValidationService {
  /**
   * Validates the reservation request
   *
   * <p>Returns a ValidationResponse with empty message if no validation errors are found otherwise
   * returns a ValidationResponse with first validation error message
   *
   * @param customer Customer for the reservation
   * @param theater Theater for the reservation
   * @param sequenceOfTheDay Sequence of the day for the reservation
   * @param ticketCount Number of people for the reservation
   * @return ValidationResponse with first error message if validation errors are found otherwise
   *     empty message
   */
  ValidationResponse validateReservation(
      @NotNull Optional<Customer> customer,
      @NotNull Optional<Theater> theater,
      int sequenceOfTheDay,
      int ticketCount);
}
