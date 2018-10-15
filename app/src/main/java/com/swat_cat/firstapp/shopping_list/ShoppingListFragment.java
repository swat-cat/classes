package com.swat_cat.firstapp.shopping_list;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.swat_cat.firstapp.R;
import com.swat_cat.firstapp.base.BaseFragment;
import com.swat_cat.firstapp.shopping_list.shopping_item.ShoppingItemFragment;

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
        presenter = new ShoppingListPresenter();
        return root;
    }

    @Override
    public void onStart() {
        super.onStart();
        presenter.setNavigator(()->{
            FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
            FragmentTransaction transaction = fragmentManager.beginTransaction();
            Fragment fragment = new ShoppingItemFragment();
            transaction.replace(R.id.content,fragment);
            transaction.addToBackStack(fragment.getClass().getSimpleName());
            transaction.commit();
        });
        presenter.start(view);
    }

    @Override
    public void onStop() {
        super.onStop();
        presenter.stop();
    }
}
