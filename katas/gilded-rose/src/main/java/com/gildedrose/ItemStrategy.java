package com.gildedrose;

interface ItemStrategy {

    boolean supports(Item item);

    void update(Item item);
}
