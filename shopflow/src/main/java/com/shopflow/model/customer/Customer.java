package com.shopflow.model.customer;

import java.util.UUID;

public record Customer(UUID id, CustomerType type) {
}
