package com.gildedrose;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class GildedRoseTest {

    private static Item updatedOnce(Item item) {
        new GildedRose(new Item[]{item}).updateQuality();
        return item;
    }

    @Nested
    class NormalItem {

        private static final String NAME = "+5 Dexterity Vest";

        @Test
        void sellInDecreasesByOne() {
            Item item = updatedOnce(new Item(NAME, 10, 20));

            assertEquals(9, item.sellIn);
        }

        @Test
        void qualityDecreasesByOneBeforeSellDate() {
            Item item = updatedOnce(new Item(NAME, 10, 20));

            assertEquals(19, item.quality);
        }

        @Test
        void qualityDecreasesByOneOnLastDayBeforeSellDate() {
            Item item = updatedOnce(new Item(NAME, 1, 20));

            assertEquals(19, item.quality);
        }

        @Test
        void qualityDegradesTwiceAsFastOnSellDate() {
            Item item = updatedOnce(new Item(NAME, 0, 20));

            assertEquals(18, item.quality);
        }

        @Test
        void qualityDegradesTwiceAsFastAfterSellDate() {
            Item item = updatedOnce(new Item(NAME, -5, 20));

            assertEquals(18, item.quality);
        }

        @Test
        void qualityIsNeverNegative() {
            Item item = updatedOnce(new Item(NAME, 5, 0));

            assertEquals(0, item.quality);
        }

        @Test
        void qualityIsNeverNegativeWhenDegradingTwiceAsFast() {
            Item item = updatedOnce(new Item(NAME, 0, 1));

            assertEquals(0, item.quality);
        }
    }
}
