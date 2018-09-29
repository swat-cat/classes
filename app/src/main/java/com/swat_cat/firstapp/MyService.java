package com.swat_cat.firstapp;

import android.app.Service;
import android.content.Intent;
import android.os.Bundle;
import android.os.IBinder;
import android.support.annotation.Nullable;

import com.swat_cat.firstapp.models.LoginData;
import com.swat_cat.firstapp.models.Weather;
import com.swat_cat.firstapp.utils.Constants;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by max_ermakov on 9/29/18.
 */

public class MyService extends Service {

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(5000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                Intent i = new Intent();
                i.setAction(Constants.BROADCAST_STRING_ACTION);
                LoginData loginData = new LoginData();
                loginData.setToken("Service Token");
                loginData.setUserId(34);
                loginData.setWeather(new Weather());
                loginData.getWeather().setTemperature(34);
                loginData.getWeather().setHumidity(23);
                loginData.getWeather().setPresure(120);
                loginData.getWeather().setStatus("Sunny");
                i.putExtra(Constants.LOGIN_DATA,loginData);
                sendBroadcast(i);
            }
        }).start();
        return START_NOT_STICKY;
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
