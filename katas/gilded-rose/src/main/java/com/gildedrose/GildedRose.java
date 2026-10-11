package com.gildedrose;

import java.util.List;

class GildedRose {
    Item[] items;
    private final List<ItemStrategy> strategies;
    private final ItemStrategy defaultStrategy;

    public GildedRose(Item[] items) {
        this(items, List.of(new SulfurasStrategy()), new RegularStrategy());
    }

    public GildedRose(Item[] items, List<ItemStrategy> strategies, ItemStrategy defaultStrategy) {
        this.items = items;
        this.strategies = strategies;
        this.defaultStrategy = defaultStrategy;
    }

    public void updateQuality() {
        for (Item item : items) {
            strategyFor(item).update(item);
        }
    }

    private ItemStrategy strategyFor(Item item) {
        return strategies.stream()
                .filter(strategy -> strategy.supports(item))
                .findFirst()
                .orElse(defaultStrategy);
    }
}
