package com.jpmc.theater.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jpmc.theater.exception.ReservationException;
import com.jpmc.theater.model.Customer;
import com.jpmc.theater.model.Reservation;
import com.jpmc.theater.model.Theater;
import com.jpmc.theater.repository.CustomerRepository;
import com.jpmc.theater.repository.TheaterRepository;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.util.Optional;

@Service
@Validated
public class DefaultReservationService implements ReservationService {

  @Autowired private ReservationFeeCalculationService feeCalculationService;
  @Autowired private ReservationValidationService reservationValidationService;
  @Autowired private TheaterRepository theaterRepository;
  @Autowired private CustomerRepository customerRepository;

  @Autowired private ObjectMapper mapper;

  @Override
  public Reservation reserve(
      @NotNull String customerId, int sequence, int howManyTickets, long theaterId) {
    Optional<Customer> customerOptional = customerRepository.getCustomerById(customerId);
    Optional<Theater> theaterOptional = theaterRepository.getTheaterById(theaterId);
    var validationResponse =
        reservationValidationService.validateReservation(
            customerOptional, theaterOptional, sequence, howManyTickets);
    if (!validationResponse.valid()) {
      throw new ReservationException(validationResponse.message());
    }

    Theater theater = theaterOptional.get();
    var showing = theater.getShowingBySequence(sequence).get();
    var fee = feeCalculationService.calculateTotalFeeForShowing(showing, howManyTickets);
    return new Reservation(theater, customerOptional.get(), showing, howManyTickets, fee);
  }
}
