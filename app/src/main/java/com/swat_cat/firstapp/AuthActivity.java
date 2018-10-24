package com.swat_cat.firstapp;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.swat_cat.firstapp.base.BaseActivity;
import com.swat_cat.firstapp.screens.login.LoginContract;
import com.swat_cat.firstapp.screens.login.LoginPresenter;
import com.swat_cat.firstapp.screens.login.LoginView;
import com.swat_cat.firstapp.data.models.LoginData;
import com.swat_cat.firstapp.utils.Constants;

public class AuthActivity extends BaseActivity {

    private static final String TAG = AuthActivity.class.getSimpleName();

    private View root;
    private LoginContract.View view;
    private LoginContract.Presenter presenter;
    private IntentFilter intentFilter;
    private BaseActivity activity;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        root = findViewById(R.id.root);
        view = new  LoginView(root);
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
            //Toast.makeText(AuthActivity.this,loginData.getWeather().toString(),Toast.LENGTH_LONG).show();
        }
    };

    @Override
    protected void onStart() {
        super.onStart();
        getBus().register(presenter);
        ((LoginView)view).setBus(getBus());
        presenter.setNavigator(getNavigator());
        presenter.start(view);
        //Paper.book().delete(Constants.ITEMS);
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
                //Toast.makeText(this,token,Toast.LENGTH_LONG).show();
            }
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        presenter.stop();
        unregisterReceiver(receiver);
        getBus().unregister(presenter);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString("email",presenter.getEmail());
    }
}
