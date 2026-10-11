package com.gildedrose.strategy;

import com.gildedrose.Item;

public interface ItemStrategy {

    boolean supports(Item item);

    void update(Item item);
}
