package com.swat_cat.firstapp;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;

import com.swat_cat.firstapp.list_example.ShoppingListAdapter;
import com.swat_cat.firstapp.data.models.ShoppingItem;
import com.swat_cat.firstapp.utils.Constants;

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
        setContentView(R.layout.activity_forgot_password);
        handler = new Handler(Looper.getMainLooper());
    }

    @Override
    protected void onResume() {
        super.onResume();
        //makeList();
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
