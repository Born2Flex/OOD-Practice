package com.gildedrose;

class RegularStrategy implements ItemStrategy {

    private static final int MIN_QUALITY = 0;

    @Override
    public boolean supports(Item item) {
        return true;
    }

    @Override
    public void update(Item item) {
        item.sellIn = item.sellIn - 1;
        int decrease = item.sellIn < 0 ? 2 : 1;
        if (item.quality > MIN_QUALITY) {
            item.quality = Math.max(MIN_QUALITY, item.quality - decrease);
        }
    }
}
