package com.swat_cat.firstapp.screens.shopping_list.shopping_item;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.swat_cat.firstapp.R;
import com.swat_cat.firstapp.base.BaseActivity;
import com.swat_cat.firstapp.base.BaseFragment;
import com.swat_cat.firstapp.screens.shopping_list.ShoppingListFragment;

public class ShoppingItemFragment extends BaseFragment {

    private ShoppingItemContract.View view;
    private ShoppingItemContract.Presenter presenter;
    private BaseActivity activity;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_shopping_item,container,false);
        activity = ((BaseActivity) getActivity());
        view = new ShoppingItemView(root);
        presenter = new ShoppingItemPresenter(activity.getNavigationBackManager());
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
    }

    @Override
    public void onStop() {
        super.onStop();
        presenter.stop();

    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        activity.getBus().unregister(presenter);

    }
}
