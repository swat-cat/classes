package com.swat_cat.firstapp.screens.shopping_list.shopping_item;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.swat_cat.firstapp.R;
import com.swat_cat.firstapp.base.BaseActivity;
import com.swat_cat.firstapp.base.BaseFragment;
import com.swat_cat.firstapp.base.action_bar.ActionBarContract;
import com.swat_cat.firstapp.screens.shopping_list.GeneralBackActionBarPresenter;
import com.swat_cat.firstapp.screens.shopping_list.ShoppingListFragment;
import com.tbruyelle.rxpermissions2.RxPermissions;

public class ShoppingItemFragment extends BaseFragment {

    private ShoppingItemContract.View view;
    private ShoppingItemContract.Presenter presenter;
    private BaseActivity activity;
    private ActionBarContract.Presenter actionBarPresenter;
   RxPermissions rxPermissions;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rxPermissions = new RxPermissions(getActivity());
        View root = inflater.inflate(R.layout.fragment_shopping_item,container,false);
        activity = ((BaseActivity) getActivity());
        actionBarPresenter = new ShoppingItemActionBarPresenter(activity, activity.getActionBarView(), R.string.create_item);
        view = new ShoppingItemView(root, activity);
        presenter = new ShoppingItemPresenter(activity.getNavigationBackManager(), rxPermissions);
        activity.getBus().register(presenter);
        return root;
    }

    @Override
    public void onStart() {
        super.onStart();
        presenter.setLoadImageCallback(type ->{
            activity.loadImage(type);
        });
        presenter.start(view);
        actionBarPresenter.start();
    }

    @Override
    public void onStop() {
        super.onStop();
        presenter.stop();
        actionBarPresenter.stop();
        actionBarPresenter = null;

    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        activity.getBus().unregister(presenter);

    }
}
