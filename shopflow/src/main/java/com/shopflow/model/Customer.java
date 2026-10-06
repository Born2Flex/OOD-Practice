package com.shopflow.model;

import java.util.UUID;

public record Customer(UUID id, CustomerType type) {
}
