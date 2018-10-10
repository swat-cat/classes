package com.swat_cat.firstapp;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.swat_cat.firstapp.base.BaseActivity;
import com.swat_cat.firstapp.base.MessageEvent;
import com.swat_cat.firstapp.login.LoginContract;
import com.swat_cat.firstapp.login.LoginNavigationCallback;
import com.swat_cat.firstapp.login.LoginPresenter;
import com.swat_cat.firstapp.login.LoginView;
import com.swat_cat.firstapp.models.LoginData;
import com.swat_cat.firstapp.utils.Constants;

import java.time.Duration;
import java.util.HashMap;

import io.paperdb.Paper;

public class MainActivity extends BaseActivity {

    private static final String TAG = MainActivity.class.getSimpleName();

    private View root;
    private LoginContract.View view;
    private LoginContract.Presenter presenter;
    private IntentFilter intentFilter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Paper.init(this);
        setContentView(R.layout.activity_main);
        root = findViewById(R.id.root);
        view = new LoginView(root);
        if (getIntent().hasExtra("TEST")){
            String text = getIntent().getStringExtra("TEST");
            if (text!=null && !text.isEmpty()){
                view.setLoginText(text);
            }
        }
        if (savedInstanceState!=null && savedInstanceState.containsKey("email")){
            String email = savedInstanceState.getString("email");
            if (email!=null) {
                view.setLoginText(email);
                ((LoginView) view).email = email;
                ((LoginView) view).initView();
            }
        }
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
            //Toast.makeText(MainActivity.this,loginData.getWeather().toString(),Toast.LENGTH_LONG).show();
        }
    };

    @Override
    protected void onStart() {
        super.onStart();
        getBus().register(presenter);
        ((LoginView)view).setBus(getBus());
        presenter.start(view);
        presenter.setNavigationCallback(new LoginNavigationCallback() {
            @Override
            public void navigateToFeed(Bundle args) {
                Intent intent = new Intent(MainActivity.this, ShoppingListActivity.class);
                startActivity(intent);
            }

            @Override
            public void navigateToForgotPassword(Bundle args) {
                Intent intent = new Intent(MainActivity.this,ForgotPasswordActivity.class);
                startActivityForResult(intent, Constants.LOGIN_RESULT);
                /*Intent intent = new Intent(Intent.ACTION_SEND);

                intent.putExtra(Intent.EXTRA_TEXT,"My awesome post");
                intent.setType("text/plain");
                startActivity(Intent.createChooser(intent,"Share"));*/
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
