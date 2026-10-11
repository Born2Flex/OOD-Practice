package com.gildedrose;

final class Quality {

    private static final int MIN = 0;
    private static final int MAX = 50;

    private Quality() {
    }

    static void increase(Item item, int amount) {
        if (item.quality < MAX) {
            item.quality = Math.min(MAX, item.quality + amount);
        }
    }

    static void decrease(Item item, int amount) {
        if (item.quality > MIN) {
            item.quality = Math.max(MIN, item.quality - amount);
        }
    }
}
