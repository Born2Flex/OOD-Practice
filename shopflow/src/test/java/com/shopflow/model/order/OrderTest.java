package com.shopflow.model.order;

import com.shopflow.model.money.Money;
import com.shopflow.model.order.exception.IllegalOrderTransitionException;
import com.shopflow.model.payment.TransactionId;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class OrderTest {

    private static final TransactionId TRANSACTION = new TransactionId("tx-1");

    private final Order order = new Order(UUID.randomUUID(), UUID.randomUUID(),
            List.of(new OrderLine(Money.of("10.00", "EUR"), 1)));

    @Test
    void subtotalSumsLineTotals() {
        Order twoLines = new Order(UUID.randomUUID(), UUID.randomUUID(), List.of(
                new OrderLine(Money.of("19.99", "EUR"), 2),
                new OrderLine(Money.of("5.00", "EUR"), 3)));

        assertThat(twoLines.subtotal()).isEqualTo(Money.of("54.98", "EUR"));
    }

    @Test
    void rejectsEmptyOrder() {
        assertThatThrownBy(() -> new Order(UUID.randomUUID(), UUID.randomUUID(), List.of()))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void rejectsLinesInDifferentCurrencies() {
        List<OrderLine> mixed = List.of(
                new OrderLine(Money.of("10.00", "EUR"), 1),
                new OrderLine(Money.of("10.00", "USD"), 1));

        assertThatThrownBy(() -> new Order(UUID.randomUUID(), UUID.randomUUID(), mixed))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Currency mismatch");
    }

    @Test
    void newOrderStartsInNew() {
        assertThat(order.status()).isEqualTo(OrderStatus.NEW);
    }

    @Test
    void onlyNewOrderCanBePaid() {
        assertThat(order.canBePaid()).isTrue();
        order.cancel();
        assertThat(order.canBePaid()).isFalse();
    }

    @Test
    void confirmPaymentStoresTransactionId() {
        assertThat(order.transactionId()).isEmpty();
        order.confirmPayment(TRANSACTION);
        assertThat(order.transactionId()).contains(TRANSACTION);
    }

    @Test
    void goesThroughHappyPath() {
        order.confirmPayment(TRANSACTION);
        assertThat(order.status()).isEqualTo(OrderStatus.PAID);
        order.ship();
        assertThat(order.status()).isEqualTo(OrderStatus.SHIPPED);
        order.deliver();
        assertThat(order.status()).isEqualTo(OrderStatus.DELIVERED);
    }

    @Test
    void canBeCancelledWhenNew() {
        order.cancel();
        assertThat(order.status()).isEqualTo(OrderStatus.CANCELLED);
    }

    @Test
    void canBeCancelledWhenPaid() {
        order.confirmPayment(TRANSACTION);
        order.cancel();
        assertThat(order.status()).isEqualTo(OrderStatus.CANCELLED);
    }

    @Test
    void cannotBeCancelledOnceShipped() {
        order.confirmPayment(TRANSACTION);
        order.ship();
        assertThatThrownBy(order::cancel)
                .isInstanceOf(IllegalOrderTransitionException.class)
                .hasMessage("Cannot cancel an order in status SHIPPED");
        assertThat(order.status()).isEqualTo(OrderStatus.SHIPPED);
    }

    @Test
    void cannotShipUnpaidOrder() {
        assertThatThrownBy(order::ship)
                .isInstanceOf(IllegalOrderTransitionException.class)
                .hasMessage("Cannot ship an order in status NEW");
    }

    @Test
    void cannotPayTwice() {
        order.confirmPayment(TRANSACTION);
        assertThatThrownBy(() -> order.confirmPayment(TRANSACTION)).isInstanceOf(IllegalOrderTransitionException.class);
    }

    @Test
    void cancelledOrderIsTerminal() {
        order.cancel();
        assertThatThrownBy(() -> order.confirmPayment(TRANSACTION)).isInstanceOf(IllegalOrderTransitionException.class);
        assertThatThrownBy(order::ship).isInstanceOf(IllegalOrderTransitionException.class);
        assertThatThrownBy(order::deliver).isInstanceOf(IllegalOrderTransitionException.class);
    }

    @Test
    void deliveredOrderIsTerminal() {
        order.confirmPayment(TRANSACTION);
        order.ship();
        order.deliver();
        assertThatThrownBy(order::cancel).isInstanceOf(IllegalOrderTransitionException.class);
        assertThatThrownBy(order::deliver).isInstanceOf(IllegalOrderTransitionException.class);
    }
}
