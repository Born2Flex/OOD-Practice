package com.gildedrose.strategy;

import com.gildedrose.Item;

class BackstagePassStrategy implements ItemStrategy {

    @Override
    public boolean supports(Item item) {
        return item.name.equals("Backstage passes to a TAFKAL80ETC concert");
    }

    @Override
    public void update(Item item) {
        if (item.sellIn <= 0) {
            item.quality = 0;
        } else {
            Quality.increase(item, increaseFor(item.sellIn));
        }
        item.sellIn = item.sellIn - 1;
    }

    private int increaseFor(int daysLeft) {
        if (daysLeft <= 5) {
            return 3;
        }
        if (daysLeft <= 10) {
            return 2;
        }
        return 1;
    }
}
