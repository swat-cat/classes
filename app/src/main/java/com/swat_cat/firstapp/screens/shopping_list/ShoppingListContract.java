package com.swat_cat.firstapp.screens.shopping_list;

import com.swat_cat.firstapp.screens.list_example.ItemBoughtCallback;
import com.swat_cat.firstapp.data.models.ShoppingItem;
import com.swat_cat.firstapp.services.Navigator;

import java.util.List;

import io.reactivex.Observable;

/**
 * Created by max_ermakov on 10/3/18.
 */

public interface ShoppingListContract {
    interface View{
        void setShoppingList(List<ShoppingItem> items, ItemBoughtCallback callback);
        void updateList(int i, ShoppingItem item);
        Observable<Object> addItemAction();
    }
    interface Presenter{
        void start(View view);
        void stop();
        void addItem();
        void setView(View view);
        void setNavigator(Navigator navigator);
    }
}