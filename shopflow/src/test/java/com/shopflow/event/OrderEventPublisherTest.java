package com.shopflow.event;

import com.shopflow.model.order.OrderStatus;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;

class OrderEventPublisherTest {

    private final OrderEventPublisher publisher = new OrderEventPublisher();
    private final OrderStatusChanged event =
            new OrderStatusChanged(UUID.randomUUID(), UUID.randomUUID(), OrderStatus.NEW, OrderStatus.PAID);

    @Test
    void notifiesEverySubscriber() {
        List<OrderStatusChanged> first = new ArrayList<>();
        List<OrderStatusChanged> second = new ArrayList<>();
        publisher.subscribe(first::add);
        publisher.subscribe(second::add);

        publisher.publish(event);

        assertThat(first).containsExactly(event);
        assertThat(second).containsExactly(event);
    }

    @Test
    void publishingWithoutSubscribersDoesNothing() {
        assertThatCode(() -> publisher.publish(event)).doesNotThrowAnyException();
    }
}
