package com.swat_cat.firstapp;


import android.os.Bundle;
import android.view.View;

import com.swat_cat.firstapp.base.BaseActivity;
import com.swat_cat.firstapp.base.action_bar.ActionBarContract;
import com.swat_cat.firstapp.base.action_bar.ActionBarView;
import com.swat_cat.firstapp.services.navigation.Screen;
import com.swat_cat.firstapp.services.navigation.ScreenType;
import com.swat_cat.firstapp.screens.shopping_list.ShoppingListFragment;

/**
 * Created by max_ermakov on 10/3/18.
 */

public class ShoppingListActivity extends BaseActivity{

    private View actionBar;
    private ActionBarContract.View actionBarView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);
        actionBar = findViewById(R.id.action_bar);
        actionBarView = new ActionBarView(actionBar);
        getNavigator().navigateTo(Screen.SHOPPING_LIST,ScreenType.FRAGMENT);
    }

    @Override
    public ActionBarContract.View getActionBarView() {
        return actionBarView;
    }
}
