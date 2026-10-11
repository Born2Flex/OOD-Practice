package com.gildedrose;

class BackstagePassStrategy implements ItemStrategy {

    private static final int MAX_QUALITY = 50;

    @Override
    public boolean supports(Item item) {
        return item.name.equals("Backstage passes to a TAFKAL80ETC concert");
    }

    @Override
    public void update(Item item) {
        if (item.sellIn <= 0) {
            item.quality = 0;
        } else if (item.quality < MAX_QUALITY) {
            item.quality = Math.min(MAX_QUALITY, item.quality + increaseFor(item.sellIn));
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
