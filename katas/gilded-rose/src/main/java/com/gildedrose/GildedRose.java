package com.gildedrose;

import com.gildedrose.strategy.ItemStrategies;
import com.gildedrose.strategy.ItemStrategy;

import java.util.List;

class GildedRose {
    Item[] items;
    private final List<ItemStrategy> strategies;

    public GildedRose(Item[] items) {
        this(items, ItemStrategies.defaults());
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
