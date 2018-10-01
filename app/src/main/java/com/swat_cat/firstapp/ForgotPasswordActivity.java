package com.swat_cat.firstapp;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.swat_cat.firstapp.list_example.ItemBoughtCallback;
import com.swat_cat.firstapp.list_example.ShoppingListAdapter;
import com.swat_cat.firstapp.models.ShoppingItem;
import com.swat_cat.firstapp.utils.Constants;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by max_ermakov on 9/29/18.
 */

public class ForgotPasswordActivity extends AppCompatActivity {

    private Handler handler;
    private RecyclerView shoppingList;
    private ShoppingListAdapter adapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_example);
        handler = new Handler(Looper.getMainLooper());
       // handler.postDelayed(this::finishScreen,5000);
        shoppingList = findViewById(R.id.shopping_list);
    }

    @Override
    protected void onResume() {
        super.onResume();
        List<ShoppingItem> list = mockShoppingList();
        LinearLayoutManager llm = new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false);
        adapter = new ShoppingListAdapter(list, new ItemBoughtCallback() {
            @Override
            public void itemBought(ShoppingItem item, boolean bought) {
                item.setBought(bought);
                Toast.makeText(ForgotPasswordActivity.this,item.getTitle()+" bought: "+item.isBought(),Toast.LENGTH_SHORT).show();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        adapter.notifyDataSetChanged();//TODO
                    }
                },50);
            }
        });
        shoppingList.setLayoutManager(llm);
        shoppingList.setAdapter(adapter);
    }

    private List<ShoppingItem> mockShoppingList() {
        List<ShoppingItem> itemList = new ArrayList<>();
        ShoppingItem item1 = new ShoppingItem();
        item1.setTitle("Milk");
        item1.setSubTitle("milk 2,5% of fat");
        item1.setImage(R.drawable.coriander);
        ShoppingItem item2 = new ShoppingItem();
        item2.setTitle("Bulb");
        item2.setSubTitle("energy saving, 20 vat");
        item2.setImage(R.drawable.pork);
        ShoppingItem item3 = new ShoppingItem();
        item3.setTitle("teeth brush");
        item3.setSubTitle("Oral B");
        item3.setImage(R.drawable.chicken_broth_170);
        itemList.add(item1);
        itemList.add(item2);
        itemList.add(item3);
        return itemList;
    }

    private void finishScreen() {
        Intent intent = new Intent();
        intent.putExtra(Constants.TOKEN,"my token");
        setResult(RESULT_OK,intent);
        finish();
    }

    @Override
    public void onBackPressed() {
        finishScreen();
        super.onBackPressed();
    }
}
