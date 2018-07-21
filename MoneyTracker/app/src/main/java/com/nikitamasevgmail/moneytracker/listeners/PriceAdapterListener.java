package com.nikitamasevgmail.moneytracker.listeners;

import com.nikitamasevgmail.moneytracker.data.Price;

public interface PriceAdapterListener {
    void onPriceClick(Price price, int pos);
    void onPriceLongClick(Price price, int pos);
}
