package com.swat_cat.firstapp.login;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.util.LogPrinter;
import android.widget.Toast;


import com.squareup.otto.Bus;
import com.squareup.otto.Subscribe;
import com.swat_cat.firstapp.base.MessageEvent;
import com.swat_cat.firstapp.services.Navigator;
import com.swat_cat.firstapp.services.navigation.Screen;
import com.swat_cat.firstapp.services.navigation.ScreenType;

import io.reactivex.Observer;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

/**
 * Created by max_ermakov on 9/26/18.
 */

public class LoginPresenter implements LoginContract.Presenter{

    private static final String USER_ID = "USER_ID";
    private LoginContract.View view;
    private CompositeDisposable subscriptions;
    private Handler handler;
    private Navigator navigator;

    @Override
    public void start(LoginContract.View view) {
        this.view =view;
        handler = new Handler(Looper.getMainLooper());
        setupView();
        setupActions();
    }

    private void setupActions() {
        view.loginInputChanged()
                .skip(2)
                .subscribe(new Observer<CharSequence>() {
            @Override
            public void onSubscribe(Disposable d) {
                subscriptions.add(d);
            }

            @Override
            public void onNext(CharSequence charSequence) {
                String loginText = charSequence.toString().trim();
                validateLogin(loginText);
            }

            @Override
            public void onError(Throwable e) {
                Log.e(LogPrinter.class.getSimpleName(),e.getLocalizedMessage());
            }

            @Override
            public void onComplete() {
            }
        });
        view.passwordInputChanged()
                .skip(2)
                .subscribe(new Observer<CharSequence>() {
            @Override
            public void onSubscribe(Disposable d) {
                subscriptions.add(d);
            }

            @Override
            public void onNext(CharSequence charSequence) {
                String password = charSequence.toString().trim();
                validatePassword(password);
            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onComplete() {

            }
        });

        view.confirmBtnAction().subscribe(new Observer<Object>() {
            @Override
            public void onSubscribe(Disposable d) {
                subscriptions.add(d);
            }

            @Override
            public void onNext(Object o) {
                //view.resizeButtonAndChangeMarging();
                login();
                //view.showMessage("Piddor!!!");
            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onComplete() {

            }
        });
        view.forgotPasswordAction().subscribe(new Observer<Object>() {
            @Override
            public void onSubscribe(Disposable d) {
                subscriptions.add(d);
            }

            @Override
            public void onNext(Object o) {
                forgotPassword();
            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onComplete() {

            }
        });
    }

    private void validatePassword(String password) {
        if (password.length() <= 6){
            view.setPasswordInputError("Password too short");
        }else {
            view.setPasswordInputError(null);
        }
    }

    private void validateLogin(String loginText) {
        if (android.util.Patterns.EMAIL_ADDRESS.matcher(loginText).matches()){
            view.setLoginInputError(null);
        }else {
            view.setLoginInputError("Invalid email");
        }

    }

    private void setupView() {
        subscriptions = new CompositeDisposable();
    }

    @Override
    public void stop() {
        subscriptions.dispose();
        subscriptions = null;
        view = null;
    }

    @Override
    public void forgotPassword() {
        navigator.navigateTo(Screen.MOVIE,ScreenType.ACTIVITY);
    }

    @Override
    public void login() {
        Log.d(LoginPresenter.class.getSimpleName(),"Password: "+view.getPasswordText()+"\n"+"Login: "+view.getLoginText());
        Bundle args = new Bundle();
        args.putInt(USER_ID,64);
        navigator.navigateTo(Screen.SHOPPING,ScreenType.ACTIVITY,args);
    }

    @Override
    public String getEmail() {
        return view.getLoginText();
    }

    @Override
    public void setNavigator(Navigator navigator) {
        this.navigator = navigator;
    }
}
