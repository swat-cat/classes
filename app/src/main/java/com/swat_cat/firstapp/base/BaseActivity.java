package com.swat_cat.firstapp.base;

import android.Manifest;
import android.animation.ObjectAnimator;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.provider.MediaStore;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.content.res.AppCompatResources;
import android.support.v7.widget.ViewUtils;
import android.text.Spanned;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jakewharton.rxbinding2.view.RxView;
import com.squareup.otto.Bus;
import com.swat_cat.firstapp.R;
import com.swat_cat.firstapp.base.dialogs.DialogShower;
import com.swat_cat.firstapp.base.dialogs.events.DialogWasDissmisedEvent;
import com.swat_cat.firstapp.base.dialogs.events.HideDialogEvent;
import com.swat_cat.firstapp.base.dialogs.events.ShowDialogEvent;

import java.io.File;
import java.io.IOException;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;
import timber.log.Timber;

/**
 * Created by max_ermakov on 1/13/17.
 */

public abstract class BaseActivity extends AppCompatActivity {
    protected final String TAG = getClass().getSimpleName();
    private Bus bus;
    private CompositeDisposable subscriptions;
    private DialogShower dialogShower;
    private CompositeDisposable imageSubs;
    private Handler handler;

    @Override
    protected void onStart() {
        super.onStart();
        Log.i(TAG, " onStart()");
        if (subscriptions == null) {
            subscriptions = new CompositeDisposable();
        }
        imageSubs = new CompositeDisposable();
    }



    @Override
    protected void onStop() {
        Log.i(TAG, " onStop()");
        if (subscriptions!=null && !subscriptions.isDisposed()){
            subscriptions.dispose();
            subscriptions.clear();
            subscriptions = null;
        }
        if (imageSubs!=null && !imageSubs.isDisposed()){
            imageSubs.dispose();
        }
        super.onStop();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.i(TAG, " onCreate()" + (savedInstanceState != null ? " recreating" : ""));
        super.onCreate(savedInstanceState);
        handler = new Handler(Looper.getMainLooper());
        bus = new Bus();
        dialogShower = new DialogShower(this);
        bus.register(dialogShower);
    }




    //public abstract void lostConnectivityAction();

    @Override
    protected void onResume() {
        Log.i(TAG, " onResume()");
        super.onResume();
        //checkConnectivity();
    }

    @Override
    protected void onPostResume() {
        Log.i(TAG, " onPostResume()");
        super.onPostResume();
    }


    @Override
    protected void onPause() {
        Log.i(TAG, " onPause()");
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        Log.i(TAG, " onDestroy()");
        bus.unregister(dialogShower);
        super.onDestroy();
    }


    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        Log.d(TAG, "onNewIntent() Intent=" + intent);
    }


    public Bus getBus() {
        return bus;
    }


    public void freeMemory() {
        System.runFinalization();
        Runtime.getRuntime().gc();
        System.gc();
    }

    public void hideKeyboard() {
        try {
            IBinder windowToken = getWindow().getDecorView().getRootView().getWindowToken();
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(windowToken, 0);
        } catch (Exception e) {
            Timber.e(e.getLocalizedMessage());
        }
    }

    public void showKeyboard(EditText editText) {
        try {
            IBinder windowToken = getWindow().getDecorView().getRootView().getWindowToken();
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.showSoftInput(editText, 0);
        } catch (Exception e) {
            Timber.e(e.getLocalizedMessage());
        }
    }
    //public abstract ActionBarContract.View getActionBarView();

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }

    public void dialogDismissed(){
        /*if (!connectivityNotifier.isConnected()){
            isNoNetworkDialogShowing = false;

            checkInternetWithDelay(INTERVAL_SHOWING_NO_NETWORK_DIALOG);
        }*/
        getBus().post(new DialogWasDissmisedEvent());
    }

}

