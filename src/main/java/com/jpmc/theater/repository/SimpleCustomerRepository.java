package com.jpmc.theater.repository;

import com.jpmc.theater.model.Customer;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * A simple implementation of {@link CustomerRepository}
 *
 * <p>Customer list is hard-coded
 */
@Repository
public class SimpleCustomerRepository implements CustomerRepository {

  private Map<String, Customer> customers;

  @PostConstruct
  private void loadCustomers() {
    customers = new HashMap<>();
    customers.put("jdoe", new Customer("John Doe", "jdoe"));
    customers.put("shsharma", new Customer("Shalandra Sharma", "shsharma"));
  }

  @Override
  public List<Customer> getCustomers() {
    return List.copyOf(customers.values());
  }

  @Override
  public Optional<Customer> getCustomerById(String customerId) {
    return Optional.ofNullable(customers.get(customerId));
  }
}
