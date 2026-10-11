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

    @Nested
    class AgedBrie {

        private static final String NAME = "Aged Brie";

        @Test
        void sellInDecreasesByOne() {
            Item item = updatedOnce(new Item(NAME, 2, 0));

            assertEquals(1, item.sellIn);
        }

        @Test
        void qualityIncreasesByOneBeforeSellDate() {
            Item item = updatedOnce(new Item(NAME, 2, 0));

            assertEquals(1, item.quality);
        }

        @Test
        void qualityIncreasesTwiceAsFastAfterSellDate() {
            Item item = updatedOnce(new Item(NAME, 0, 10));

            assertEquals(12, item.quality);
        }

        @Test
        void qualityIsNeverMoreThanFifty() {
            Item item = updatedOnce(new Item(NAME, 5, 50));

            assertEquals(50, item.quality);
        }

        @Test
        void qualityIsNeverMoreThanFiftyWhenIncreasingTwiceAsFast() {
            Item item = updatedOnce(new Item(NAME, 0, 49));

            assertEquals(50, item.quality);
        }
    }

    @Nested
    class Sulfuras {

        private static final String NAME = "Sulfuras, Hand of Ragnaros";

        @Test
        void neverHasToBeSold() {
            Item item = updatedOnce(new Item(NAME, 5, 80));

            assertEquals(5, item.sellIn);
        }

        @Test
        void qualityNeverChanges() {
            Item item = updatedOnce(new Item(NAME, 5, 80));

            assertEquals(80, item.quality);
        }

        @Test
        void qualityNeverChangesAfterSellDate() {
            Item item = updatedOnce(new Item(NAME, -1, 80));

            assertEquals(80, item.quality);
        }
    }
}
