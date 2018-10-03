package com.swat_cat.firstapp;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import com.swat_cat.firstapp.base.BaseActivity;
import com.swat_cat.firstapp.shopping_list.ShoppingListFragment;

/**
 * Created by max_ermakov on 10/3/18.
 */

public class ShoppingListActivity extends BaseActivity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        Fragment fragment = new ShoppingListFragment();
        transaction.add(R.id.content,fragment);
        transaction.commit();
    }
}
