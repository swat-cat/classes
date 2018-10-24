package com.swat_cat.firstapp.shopping_list;

import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.jakewharton.rxbinding2.view.RxView;
import com.swat_cat.firstapp.R;
import com.swat_cat.firstapp.list_example.ItemBoughtCallback;
import com.swat_cat.firstapp.list_example.ShoppingListAdapter;
import com.swat_cat.firstapp.data.models.ShoppingItem;

import java.util.List;

import io.reactivex.Observable;

/**
 * Created by max_ermakov on 10/3/18.
 */

public class ShoppingListView implements ShoppingListContract.View{

    private View root;
    private FloatingActionButton addButton;
    private RecyclerView shoppingList;
    private ShoppingListAdapter adapter;

    public ShoppingListView(View root) {
        this.root = root;
        initView();
    }

    private void initView() {
        addButton = root.findViewById(R.id.fab);
        shoppingList = root.findViewById(R.id.shopping_list);
    }

    @Override
    public void setShoppingList(List<ShoppingItem> items, ItemBoughtCallback callback) {
        LinearLayoutManager llm = new LinearLayoutManager(root.getContext(),LinearLayoutManager.VERTICAL,false);
        adapter = new ShoppingListAdapter(items, callback);
        shoppingList.setLayoutManager(llm);
        shoppingList.setAdapter(adapter);
    }

    @Override
    public Observable<Object> addItemAction() {
        return RxView.clicks(addButton);
    }

    @Override
    public void updateList(int i, ShoppingItem item) {


        adapter.notifyItemChanged(i,item);
        //adapter.notifyDataSetChanged();
    }
}
