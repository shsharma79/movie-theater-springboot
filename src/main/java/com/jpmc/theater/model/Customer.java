package com.jpmc.theater.model;

import jakarta.validation.constraints.NotNull;

import java.util.Objects;

/**
 * Model for Customer
 *
 * @param name Name of the customer
 * @param id Id of the customer
 */
public record Customer(@NotNull String name, @NotNull String id) {
  public Customer {
    Objects.requireNonNull(name);
    Objects.requireNonNull(id);
  }
}
