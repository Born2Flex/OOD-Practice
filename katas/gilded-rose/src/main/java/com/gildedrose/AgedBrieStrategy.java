package com.gildedrose;

class AgedBrieStrategy implements ItemStrategy {

    @Override
    public boolean supports(Item item) {
        return item.name.equals("Aged Brie");
    }

    @Override
    public void update(Item item) {
        item.sellIn = item.sellIn - 1;
        Quality.increase(item, item.sellIn < 0 ? 2 : 1);
    }
}
