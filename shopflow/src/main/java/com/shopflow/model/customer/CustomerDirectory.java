package com.shopflow.model.customer;

import java.util.Optional;
import java.util.UUID;

@FunctionalInterface
public interface CustomerDirectory {

    Optional<Customer> findById(UUID id);
}
