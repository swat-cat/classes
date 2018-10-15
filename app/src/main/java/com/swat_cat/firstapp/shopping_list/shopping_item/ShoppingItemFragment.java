package com.swat_cat.firstapp.shopping_list.shopping_item;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.swat_cat.firstapp.R;
import com.swat_cat.firstapp.base.BaseFragment;
import com.swat_cat.firstapp.models.ShoppingItem;

public class ShoppingItemFragment extends BaseFragment {

    ShoppingItemContract.Presenter presenter;
    ShoppingItemContract.View view;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.shopping_item, container, false);
        return root;
    }
}
