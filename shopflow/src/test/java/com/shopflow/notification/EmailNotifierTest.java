package com.shopflow.notification;

import com.shopflow.event.OrderStatusChanged;
import com.shopflow.model.customer.Customer;
import com.shopflow.model.customer.CustomerType;
import com.shopflow.model.order.OrderStatus;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class EmailNotifierTest {

    private final Customer customer = new Customer(UUID.randomUUID(), CustomerType.REGULAR, "jane@example.com");
    private final InMemoryEmailSender sender = new InMemoryEmailSender();
    private final EmailNotifier notifier = new EmailNotifier(
            id -> id.equals(customer.id()) ? Optional.of(customer) : Optional.empty(), sender);

    @ParameterizedTest
    @CsvSource({
            "NEW, PAID, Payment received",
            "PAID, SHIPPED, Your order is on its way",
            "SHIPPED, DELIVERED, Your order has arrived",
            "PAID, CANCELLED, Your order was cancelled"
    })
    void emailsCustomerOnStatusChange(OrderStatus from, OrderStatus to, String subject) {
        UUID orderId = UUID.randomUUID();

        notifier.onStatusChanged(new OrderStatusChanged(orderId, customer.id(), from, to));

        assertThat(sender.sent()).containsExactly(
                new EmailMessage("jane@example.com", subject, "Order %s is now %s.".formatted(orderId, to)));
    }

    @Test
    void failsForUnknownCustomer() {
        OrderStatusChanged event = new OrderStatusChanged(UUID.randomUUID(), UUID.randomUUID(), OrderStatus.NEW, OrderStatus.PAID);

        assertThatThrownBy(() -> notifier.onStatusChanged(event)).isInstanceOf(IllegalStateException.class);
        assertThat(sender.sent()).isEmpty();
    }
}
