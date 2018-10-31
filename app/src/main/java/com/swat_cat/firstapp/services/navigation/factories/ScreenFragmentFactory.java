package com.swat_cat.firstapp.services.navigation.factories;


import android.support.v4.app.Fragment;

import com.swat_cat.firstapp.base.App;
import com.swat_cat.firstapp.services.navigation.Screen;
import com.swat_cat.firstapp.screens.shopping_list.ShoppingListFragment;
import com.swat_cat.firstapp.screens.shopping_list.shopping_item.ShoppingItemFragment;

public class ScreenFragmentFactory {

    public Fragment getFragmentByType(Screen screen){
        Class<? extends Fragment> clazz = getFragmentClassByType(screen);
        return Fragment.instantiate(App.getInstance(),clazz.getName());
    }

    public Class<? extends Fragment> getFragmentClassByType(Screen screen) {
        switch (screen){
            case SHOPPING_LIST:
                return ShoppingListFragment.class;
            case ITEM_DETAILS:
                return ShoppingItemFragment.class;
                default: return  ShoppingListFragment.class;
        }
    }
}
