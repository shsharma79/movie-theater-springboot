package com.jpmc.theater.service;

import com.jpmc.theater.model.Customer;
import com.jpmc.theater.model.Showing;
import com.jpmc.theater.model.Theater;
import com.jpmc.theater.util.ValidationResponse;
import jakarta.validation.constraints.NotNull;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.util.Optional;

import static com.jpmc.theater.exception.ReservationException.*;

/** Default implementation of {@link ReservationValidationService} */
@Service
@Validated
public class DefaultReservationValidationService implements ReservationValidationService {

  private final ValidationResponse SUCCESS_RESPONSE = new ValidationResponse(true, "");

  @Override
  public ValidationResponse validateReservation(
      @NotNull Optional<Customer> customer,
      @NotNull Optional<Theater> theater,
      int sequenceOfTheDay,
      int ticketCount) {
    var response = validateCustomer(customer);
    if (!response.valid()) return response;
    response = validateTheater(theater);
    if (!response.valid()) return response;
    return validateShowing(theater.get(), sequenceOfTheDay);
  }

  private ValidationResponse validateCustomer(Optional<Customer> customer) {
    if (customer.isEmpty()) {
      return new ValidationResponse(false, CUSTOMER_NOT_FOUND);
    }
    return SUCCESS_RESPONSE;
  }

  private ValidationResponse validateTheater(Optional<Theater> theater) {
    if (theater.isEmpty()) {
      return new ValidationResponse(false, THEATER_NOT_FOUND);
    }
    return SUCCESS_RESPONSE;
  }

  private ValidationResponse validateShowing(Theater theater, int sequence) {
    Optional<Showing> showingOptional = theater.getShowingBySequence(sequence);
    if (showingOptional.isEmpty()) {
      return new ValidationResponse(false, SHOWING_NOT_FOUND);
    }
    return SUCCESS_RESPONSE;
  }
}
