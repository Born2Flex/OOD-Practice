package com.gildedrose.strategy;

import java.util.List;

public final class ItemStrategies {

    private ItemStrategies() {
    }

    public static List<ItemStrategy> defaults() {
        return List.of(
                new SulfurasStrategy(),
                new AgedBrieStrategy(),
                new BackstagePassStrategy(),
                new ConjuredStrategy(),
                new RegularStrategy());
    }
}
