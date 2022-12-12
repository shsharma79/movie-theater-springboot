package com.jpmc.theater.repository;

import com.jpmc.theater.model.Customer;

import java.util.List;
import java.util.Optional;

/** Interface that allows access to Customers setup in the application */
public interface CustomerRepository {

  /**
   * List of all the customers that are setup in the application
   *
   * <p>If no customers are setup, it should return an empty list
   *
   * @return List of all the customers that are setup in the application
   */
  List<Customer> getCustomers();

  /**
   * Returns the customer for the input customer id or an empty optional if no customer found for
   * input id
   *
   * @param customerId the customer id
   * @return Customer for the input id or empty optional if no customer found
   */
  Optional<Customer> getCustomerById(String customerId);
}
