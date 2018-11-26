package com.swat_cat.firstapp.screens.shopping_list;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.swat_cat.firstapp.R;
import com.swat_cat.firstapp.base.BaseActivity;
import com.swat_cat.firstapp.base.BaseFragment;
import com.swat_cat.firstapp.base.action_bar.ActionBarContract;

/**
 * Created by max_ermakov on 10/3/18.
 */

public class ShoppingListFragment extends BaseFragment {

    private ShoppingListContract.View view;
    private ShoppingListContract.Presenter presenter;
    private BaseActivity activity;
    private ActionBarContract.Presenter actionBarPresenter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_list_example,container,false);
        activity = (BaseActivity)getActivity();
        view = new ShoppingListView(root);
        presenter = new ShoppingListPresenter();
        actionBarPresenter = new GeneralBackActionBarPresenter(activity,activity.getActionBarView(), R.string.shopping_list);
        return root;
    }

    @Override
    public void onStart() {
        super.onStart();
        actionBarPresenter.start();
        presenter.setNavigator(activity.getNavigator());
        presenter.start(view);
    }

    @Override
    public void onStop() {
        super.onStop();
        actionBarPresenter.stop();
        presenter.stop();
    }
}
