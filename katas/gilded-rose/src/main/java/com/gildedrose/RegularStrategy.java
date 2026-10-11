package com.gildedrose;

class RegularStrategy implements ItemStrategy {

    @Override
    public boolean supports(Item item) {
        return true;
    }

    @Override
    public void update(Item item) {
        item.sellIn = item.sellIn - 1;
        Quality.decrease(item, item.sellIn < 0 ? 2 : 1);
    }
}
