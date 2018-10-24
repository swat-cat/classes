package com.swat_cat.firstapp.list_example;

import com.swat_cat.firstapp.data.models.ShoppingItem;

/**
 * Created by max_ermakov on 10/1/18.
 */

public interface ItemBoughtCallback {
    void itemBought(ShoppingItem item, boolean bought, int position);
}
