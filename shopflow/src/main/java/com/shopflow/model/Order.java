package com.shopflow.model;

import com.shopflow.model.state.NewState;
import com.shopflow.model.state.OrderState;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class Order {

    private final UUID id;
    private final UUID customerId;
    private final List<OrderLine> lines;
    private OrderState state = new NewState();
    private TransactionId transactionId;

    public Order(UUID id, UUID customerId, List<OrderLine> lines) {
        if (lines.isEmpty()) {
            throw new IllegalArgumentException("Order must have at least one line");
        }
        requireSingleCurrency(lines);
        this.id = id;
        this.customerId = customerId;
        this.lines = List.copyOf(lines);
    }

    public boolean canBePaid() {
        return state.canPay();
    }

    public void confirmPayment(TransactionId transactionId) {
        state = state.pay();
        this.transactionId = transactionId;
    }

    public void ship() {
        state = state.ship();
    }

    public void deliver() {
        state = state.deliver();
    }

    public void cancel() {
        state = state.cancel();
    }

    public Money subtotal() {
        return lines.stream()
                .map(OrderLine::total)
                .reduce(Money::add)
                .orElseThrow();
    }

    public UUID id() {
        return id;
    }

    public UUID customerId() {
        return customerId;
    }

    public List<OrderLine> lines() {
        return lines;
    }

    public Optional<TransactionId> transactionId() {
        return Optional.ofNullable(transactionId);
    }

    public OrderStatus status() {
        return state.status();
    }

    private static void requireSingleCurrency(List<OrderLine> lines) {
        long currencies = lines.stream().map(line -> line.unitPrice().currency()).distinct().count();
        if (currencies > 1) {
            throw new IllegalArgumentException("Currency mismatch: all order lines must use the same currency");
        }
    }
}
