package com.gildedrose;

class ConjuredStrategy implements ItemStrategy {

    @Override
    public boolean supports(Item item) {
        return item.name.startsWith("Conjured");
    }

    @Override
    public void update(Item item) {
        item.sellIn = item.sellIn - 1;
        Quality.decrease(item, item.sellIn < 0 ? 4 : 2);
    }
}
