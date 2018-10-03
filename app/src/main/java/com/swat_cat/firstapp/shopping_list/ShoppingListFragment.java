package com.swat_cat.firstapp.shopping_list;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.swat_cat.firstapp.R;
import com.swat_cat.firstapp.base.BaseFragment;

/**
 * Created by max_ermakov on 10/3/18.
 */

public class ShoppingListFragment extends BaseFragment {

    private ShoppingListContract.View view;
    private ShoppingListContract.Presenter presenter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_list_example,container,false);
        view = new ShoppingListView(root);
        return root;
    }

    @Override
    public void onStart() {
        super.onStart();
        presenter.start(view);
    }

    @Override
    public void onStop() {
        super.onStop();
        presenter.stop();
    }
}
