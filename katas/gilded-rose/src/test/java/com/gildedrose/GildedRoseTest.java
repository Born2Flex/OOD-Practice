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
        void shouldDecreaseSellInByOneWhenDayPasses() {
            Item item = updatedOnce(new Item(NAME, 10, 20));

            assertEquals(9, item.sellIn);
        }

        @Test
        void shouldDecreaseQualityByOneWhenBeforeSellDate() {
            Item item = updatedOnce(new Item(NAME, 10, 20));

            assertEquals(19, item.quality);
        }

        @Test
        void shouldDecreaseQualityByOneWhenOneDayLeft() {
            Item item = updatedOnce(new Item(NAME, 1, 20));

            assertEquals(19, item.quality);
        }

        @Test
        void shouldDecreaseQualityByTwoWhenSellDateReached() {
            Item item = updatedOnce(new Item(NAME, 0, 20));

            assertEquals(18, item.quality);
        }

        @Test
        void shouldDecreaseQualityByTwoWhenSellDatePassed() {
            Item item = updatedOnce(new Item(NAME, -5, 20));

            assertEquals(18, item.quality);
        }

        @Test
        void shouldNotDecreaseQualityBelowZeroWhenQualityIsZero() {
            Item item = updatedOnce(new Item(NAME, 5, 0));

            assertEquals(0, item.quality);
        }

        @Test
        void shouldNotDecreaseQualityBelowZeroWhenDecreasingByTwo() {
            Item item = updatedOnce(new Item(NAME, 0, 1));

            assertEquals(0, item.quality);
        }
    }

    @Nested
    class AgedBrie {

        private static final String NAME = "Aged Brie";

        @Test
        void shouldDecreaseSellInByOneWhenDayPasses() {
            Item item = updatedOnce(new Item(NAME, 2, 0));

            assertEquals(1, item.sellIn);
        }

        @Test
        void shouldIncreaseQualityByOneWhenBeforeSellDate() {
            Item item = updatedOnce(new Item(NAME, 2, 0));

            assertEquals(1, item.quality);
        }

        @Test
        void shouldIncreaseQualityByTwoWhenSellDateReached() {
            Item item = updatedOnce(new Item(NAME, 0, 10));

            assertEquals(12, item.quality);
        }

        @Test
        void shouldNotIncreaseQualityAboveFiftyWhenQualityIsFifty() {
            Item item = updatedOnce(new Item(NAME, 5, 50));

            assertEquals(50, item.quality);
        }

        @Test
        void shouldNotIncreaseQualityAboveFiftyWhenIncreasingByTwo() {
            Item item = updatedOnce(new Item(NAME, 0, 49));

            assertEquals(50, item.quality);
        }
    }

    @Nested
    class Sulfuras {

        private static final String NAME = "Sulfuras, Hand of Ragnaros";

        @Test
        void shouldNotChangeSellInWhenDayPasses() {
            Item item = updatedOnce(new Item(NAME, 5, 80));

            assertEquals(5, item.sellIn);
        }

        @Test
        void shouldNotChangeQualityWhenBeforeSellDate() {
            Item item = updatedOnce(new Item(NAME, 5, 80));

            assertEquals(80, item.quality);
        }

        @Test
        void shouldNotChangeQualityWhenSellDatePassed() {
            Item item = updatedOnce(new Item(NAME, -1, 80));

            assertEquals(80, item.quality);
        }
    }

    @Nested
    class BackstagePass {

        private static final String NAME = "Backstage passes to a TAFKAL80ETC concert";

        @Test
        void shouldDecreaseSellInByOneWhenDayPasses() {
            Item item = updatedOnce(new Item(NAME, 15, 20));

            assertEquals(14, item.sellIn);
        }

        @Test
        void shouldIncreaseQualityByOneWhenMoreThanTenDaysLeft() {
            Item item = updatedOnce(new Item(NAME, 11, 20));

            assertEquals(21, item.quality);
        }

        @Test
        void shouldIncreaseQualityByTwoWhenTenDaysLeft() {
            Item item = updatedOnce(new Item(NAME, 10, 20));

            assertEquals(22, item.quality);
        }

        @Test
        void shouldIncreaseQualityByTwoWhenSixDaysLeft() {
            Item item = updatedOnce(new Item(NAME, 6, 20));

            assertEquals(22, item.quality);
        }

        @Test
        void shouldIncreaseQualityByThreeWhenFiveDaysLeft() {
            Item item = updatedOnce(new Item(NAME, 5, 20));

            assertEquals(23, item.quality);
        }

        @Test
        void shouldIncreaseQualityByThreeWhenOneDayLeft() {
            Item item = updatedOnce(new Item(NAME, 1, 20));

            assertEquals(23, item.quality);
        }

        @Test
        void shouldDropQualityToZeroWhenConcertPassed() {
            Item item = updatedOnce(new Item(NAME, 0, 20));

            assertEquals(0, item.quality);
        }

        @Test
        void shouldNotIncreaseQualityAboveFiftyWhenIncreasingByTwo() {
            Item item = updatedOnce(new Item(NAME, 10, 49));

            assertEquals(50, item.quality);
        }

        @Test
        void shouldNotIncreaseQualityAboveFiftyWhenIncreasingByThree() {
            Item item = updatedOnce(new Item(NAME, 5, 48));

            assertEquals(50, item.quality);
        }
    }

    @Nested
    class Conjured {

        private static final String NAME = "Conjured Mana Cake";

        @Test
        void shouldDecreaseSellInByOneWhenDayPasses() {
            Item item = updatedOnce(new Item(NAME, 3, 6));

            assertEquals(2, item.sellIn);
        }

        @Test
        void shouldDecreaseQualityByTwoWhenBeforeSellDate() {
            Item item = updatedOnce(new Item(NAME, 3, 6));

            assertEquals(4, item.quality);
        }

        @Test
        void shouldDecreaseQualityByFourWhenSellDateReached() {
            Item item = updatedOnce(new Item(NAME, 0, 10));

            assertEquals(6, item.quality);
        }

        @Test
        void shouldDecreaseQualityByFourWhenSellDatePassed() {
            Item item = updatedOnce(new Item(NAME, -5, 10));

            assertEquals(6, item.quality);
        }

        @Test
        void shouldNotDecreaseQualityBelowZeroWhenQualityIsZero() {
            Item item = updatedOnce(new Item(NAME, 5, 0));

            assertEquals(0, item.quality);
        }

        @Test
        void shouldNotDecreaseQualityBelowZeroWhenDecreasingByFour() {
            Item item = updatedOnce(new Item(NAME, 0, 3));

            assertEquals(0, item.quality);
        }

        @Test
        void shouldDecreaseQualityByTwoWhenNameStartsWithConjured() {
            Item item = updatedOnce(new Item("Conjured Sword", 5, 10));

            assertEquals(8, item.quality);
        }
    }

    @Test
    void shouldUpdateEveryItemWhenInventoryHasManyItems() {
        Item vest = new Item("+5 Dexterity Vest", 10, 20);
        Item brie = new Item("Aged Brie", 2, 0);

        new GildedRose(new Item[]{vest, brie}).updateQuality();

        assertEquals(19, vest.quality);
        assertEquals(1, brie.quality);
    }
}
