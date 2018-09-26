package com.swat_cat.firstapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.swat_cat.firstapp.login.LoginContract;
import com.swat_cat.firstapp.login.LoginPresenter;
import com.swat_cat.firstapp.login.LoginView;

public class MainActivity extends AppCompatActivity {

    private View root;
    private LoginContract.View view;
    private LoginContract.Presenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        root = findViewById(R.id.root);
        view = new LoginView(root);
        presenter = new LoginPresenter();
    }

    @Override
    protected void onStart() {
        super.onStart();
        presenter.start(view);
    }

    @Override
    protected void onStop() {
        super.onStop();
        presenter.stop();
    }
}
