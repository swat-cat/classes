package com.swat_cat.firstapp;


import android.os.Bundle;

import com.swat_cat.firstapp.base.BaseActivity;
import com.swat_cat.firstapp.base.action_bar.ActionBarContract;
import com.swat_cat.firstapp.services.navigation.Screen;
import com.swat_cat.firstapp.services.navigation.ScreenType;
import com.swat_cat.firstapp.screens.shopping_list.ShoppingListFragment;

/**
 * Created by max_ermakov on 10/3/18.
 */

public class ShoppingListActivity extends BaseActivity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);
        getNavigator().navigateTo(Screen.SHOPPING_LIST,ScreenType.FRAGMENT);
    }

    @Override
    public ActionBarContract.View getActionBarView() {
        return null;
    }
}
