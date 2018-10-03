package com.swat_cat.firstapp.shopping_list;

import com.swat_cat.firstapp.list_example.ItemBoughtCallback;
import com.swat_cat.firstapp.login.LoginContract;
import com.swat_cat.firstapp.models.ShoppingItem;

import java.util.List;

import io.reactivex.Observable;

/**
 * Created by max_ermakov on 10/3/18.
 */

public interface ShoppingListContract {
    interface View{
        void setShoppingList(List<ShoppingItem> items, ItemBoughtCallback callback);
        void updateList();
        Observable<Object> addItemAction();
    }
    interface Presenter{
        void start(View view);
        void stop();
        void addItem();
        void setView(View view);
    }
}
