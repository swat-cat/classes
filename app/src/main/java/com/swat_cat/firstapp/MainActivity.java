package com.swat_cat.firstapp;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.swat_cat.firstapp.login.LoginContract;
import com.swat_cat.firstapp.login.LoginNavigationCallback;
import com.swat_cat.firstapp.login.LoginPresenter;
import com.swat_cat.firstapp.login.LoginView;
import com.swat_cat.firstapp.models.LoginData;
import com.swat_cat.firstapp.utils.Constants;

import java.time.Duration;
import java.util.HashMap;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getSimpleName();

    private View root;
    private LoginContract.View view;
    private LoginContract.Presenter presenter;
    private IntentFilter intentFilter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        root = findViewById(R.id.root);
        view = new LoginView(root);
        presenter = new LoginPresenter();
        intentFilter = new IntentFilter();
        intentFilter.addAction(Constants.BROADCAST_STRING_ACTION);
    }

    private BroadcastReceiver receiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            LoginData loginData = intent.getParcelableExtra(Constants.LOGIN_DATA);
            Log.d(TAG,"Weather temperature: "+ loginData.getWeather().getTemperature());
            Log.d(TAG,"Token: "+loginData.getToken());
            Log.d(TAG,"User id: "+loginData.getUserId());
            Toast.makeText(MainActivity.this,loginData.getWeather().toString(),Toast.LENGTH_LONG).show();
        }
    };

    @Override
    protected void onStart() {
        super.onStart();
        presenter.start(view);
        presenter.setNavigationCallback(new LoginNavigationCallback() {
            @Override
            public void navigateToFeed(Bundle args) {
            }

            @Override
            public void navigateToForgotPassword(Bundle args) {
                Intent intent = new Intent(MainActivity.this,ForgotPasswordActivity.class);
                startActivityForResult(intent, Constants.LOGIN_RESULT);
            }
        });
        registerReceiver(receiver,intentFilter);
        Intent serviceIntent = new Intent(this,MyService.class);
        startService(serviceIntent);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK){
            if (requestCode == Constants.LOGIN_RESULT){
                String token = data.getStringExtra(Constants.TOKEN);
                Toast.makeText(this,token,Toast.LENGTH_LONG).show();
            }
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        presenter.stop();
        unregisterReceiver(receiver);
    }
}
