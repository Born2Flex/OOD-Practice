package com.gildedrose.strategy;

import com.gildedrose.Item;

class SulfurasStrategy implements ItemStrategy {

    @Override
    public boolean supports(Item item) {
        return item.name.equals("Sulfuras, Hand of Ragnaros");
    }

    @Override
    public void update(Item item) {
    }
}
