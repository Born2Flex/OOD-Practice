package com.shopflow.service;

import com.shopflow.model.customer.Customer;
import com.shopflow.model.customer.CustomerType;
import com.shopflow.model.discount.CustomerTypeSpecification;
import com.shopflow.model.discount.Discount;
import com.shopflow.model.discount.PercentageDiscount;
import com.shopflow.model.discount.PricingContext;
import com.shopflow.model.discount.Specification;
import com.shopflow.model.discount.StackedDiscount;
import com.shopflow.model.money.Money;
import com.shopflow.model.money.Percentage;
import com.shopflow.model.order.Order;
import com.shopflow.model.order.OrderLine;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class PricingServiceTest {

    private static final Specification<PricingContext> VIP = new CustomerTypeSpecification(CustomerType.VIP);
    private static final Specification<PricingContext> ANYONE = context -> true;

    private final Customer vip = new Customer(UUID.randomUUID(), CustomerType.VIP, "vip@example.com");
    private final Customer regular = new Customer(UUID.randomUUID(), CustomerType.REGULAR, "regular@example.com");

    @Test
    void vipGetsPercentageDiscount() {
        PricingService pricing = new PricingService(new PercentageDiscount(Percentage.of(10), VIP));

        assertThat(pricing.total(orderFor(vip, "100.00"), vip)).isEqualTo(Money.of("90.00", "EUR"));
    }

    @Test
    void regularCustomerPaysFullPrice() {
        PricingService pricing = new PricingService(new PercentageDiscount(Percentage.of(10), VIP));

        assertThat(pricing.total(orderFor(regular, "100.00"), regular)).isEqualTo(Money.of("100.00", "EUR"));
    }

    @Test
    void stackedPercentagesAddUp() {
        PricingService pricing = new PricingService(StackedDiscount.of(
                new PercentageDiscount(Percentage.of(10), VIP),
                new PercentageDiscount(Percentage.of(5), ANYONE)));

        assertThat(pricing.total(orderFor(vip, "100.00"), vip)).isEqualTo(Money.of("85.00", "EUR"));
        assertThat(pricing.total(orderFor(regular, "100.00"), regular)).isEqualTo(Money.of("95.00", "EUR"));
    }

    @Test
    void combinedConditionsUseSpecificationOperators() {
        Discount notForVip = new PercentageDiscount(Percentage.of(20), VIP.not());
        PricingService pricing = new PricingService(notForVip);

        assertThat(pricing.total(orderFor(vip, "100.00"), vip)).isEqualTo(Money.of("100.00", "EUR"));
        assertThat(pricing.total(orderFor(regular, "100.00"), regular)).isEqualTo(Money.of("80.00", "EUR"));
    }

    @Test
    void totalNeverGoesBelowZero() {
        PricingService pricing = new PricingService(StackedDiscount.of(
                new PercentageDiscount(Percentage.of(60), ANYONE),
                new PercentageDiscount(Percentage.of(60), ANYONE)));

        assertThat(pricing.total(orderFor(vip, "100.00"), vip)).isEqualTo(Money.of("0.00", "EUR"));
    }

    @Test
    void rejectsCustomerWhoDoesNotOwnTheOrder() {
        PricingService pricing = new PricingService(StackedDiscount.of());

        assertThatThrownBy(() -> pricing.total(orderFor(regular, "100.00"), vip))
                .isInstanceOf(IllegalArgumentException.class);
    }

    private static Order orderFor(Customer customer, String amount) {
        return new Order(UUID.randomUUID(), customer.id(), List.of(new OrderLine(Money.of(amount, "EUR"), 1)));
    }
}
