package com.shopflow.notification;

import com.shopflow.event.OrderEventListener;
import com.shopflow.event.OrderStatusChanged;
import com.shopflow.model.customer.Customer;
import com.shopflow.model.customer.CustomerDirectory;

public class EmailNotifier implements OrderEventListener {

    private final CustomerDirectory customers;
    private final EmailSender sender;

    public EmailNotifier(CustomerDirectory customers, EmailSender sender) {
        this.customers = customers;
        this.sender = sender;
    }

    @Override
    public void onStatusChanged(OrderStatusChanged event) {
        String subject = switch (event.to()) {
            case NEW -> null;
            case PAID -> "Payment received";
            case SHIPPED -> "Your order is on its way";
            case DELIVERED -> "Your order has arrived";
            case CANCELLED -> "Your order was cancelled";
        };
        if (subject == null) {
            return;
        }
        Customer customer = customers.findById(event.customerId())
                .orElseThrow(() -> new IllegalStateException("Unknown customer " + event.customerId()));
        sender.send(new EmailMessage(customer.email(), subject, "Order %s is now %s.".formatted(event.orderId(), event.to())));
    }
}
