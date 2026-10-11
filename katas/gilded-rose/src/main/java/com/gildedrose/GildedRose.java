package com.gildedrose;

import java.util.List;

class GildedRose {
    Item[] items;
    private final List<ItemStrategy> strategies;

    public GildedRose(Item[] items) {
        this(items, List.of(
                new SulfurasStrategy(),
                new AgedBrieStrategy(),
                new BackstagePassStrategy(),
                new ConjuredStrategy(),
                new RegularStrategy()));
    }

    public GildedRose(Item[] items, List<ItemStrategy> strategies) {
        this.items = items;
        this.strategies = strategies;
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
                .orElseThrow();
    }
}
