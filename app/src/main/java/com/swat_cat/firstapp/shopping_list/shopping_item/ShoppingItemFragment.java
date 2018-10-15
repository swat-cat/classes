package com.swat_cat.firstapp.shopping_list.shopping_item;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.swat_cat.firstapp.R;
import com.swat_cat.firstapp.base.BaseFragment;

public class ShoppingItemFragment extends BaseFragment {

    private ShoppingItemContract.View view;
    private ShoppingItemContract.Presenter presenter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_shopping_item,container,false);
        return root;
    }
}
