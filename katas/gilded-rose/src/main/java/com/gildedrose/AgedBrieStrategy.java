package com.gildedrose;

class AgedBrieStrategy implements ItemStrategy {

    private static final int MAX_QUALITY = 50;

    @Override
    public boolean supports(Item item) {
        return item.name.equals("Aged Brie");
    }

    @Override
    public void update(Item item) {
        item.sellIn = item.sellIn - 1;
        int increase = item.sellIn < 0 ? 2 : 1;
        if (item.quality < MAX_QUALITY) {
            item.quality = Math.min(MAX_QUALITY, item.quality + increase);
        }
    }
}
