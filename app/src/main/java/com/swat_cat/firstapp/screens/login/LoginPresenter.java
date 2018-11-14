package com.swat_cat.firstapp.screens.login;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.util.LogPrinter;


import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.swat_cat.firstapp.base.BaseActivity;
import com.swat_cat.firstapp.services.Navigator;
import com.swat_cat.firstapp.services.navigation.Screen;
import com.swat_cat.firstapp.services.navigation.ScreenType;

import io.reactivex.Observer;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import timber.log.Timber;

/**
 * Created by max_ermakov on 9/26/18.
 */

public class LoginPresenter implements LoginContract.Presenter{

    private static final String USER_ID = "USER_ID";
    private LoginContract.View view;
    private CompositeDisposable subscriptions;
    private Handler handler;
    private Navigator navigator;
    private AuthState state = AuthState.SIGNIN;
    private FirebaseAuth mAuth;
    private BaseActivity activity;

    public LoginPresenter(BaseActivity activity) {
        this.activity = activity;
    }

    @Override
    public void start(LoginContract.View view) {
        this.view =view;
        mAuth = FirebaseAuth.getInstance();
        handler = new Handler(Looper.getMainLooper());
        setupView();
        setupActions();
    }

    private void setupActions() {
        view.confirmBtnAction().subscribe(new Observer<Object>() {
            @Override
            public void onSubscribe(Disposable d) {
                subscriptions.add(d);
            }

            @Override
            public void onNext(Object o) {
                login();
            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onComplete() {

            }
        });
        view.forgotPasswordAction().subscribe(
                o->{
                    if (state == AuthState.SIGNIN){
                        state = AuthState.SIGNUP;
                        view.changeAuthState(state);
                    }else {
                        state = AuthState.SIGNIN;
                        view.changeAuthState(state);
                    }
                }
        );
    }

    private boolean validatePassword(String password) {
        if (password.length() <= 6){
            view.setPasswordInputError("Password too short");
            return false;
        }else {
            view.setPasswordInputError(null);
            return true;
        }
    }

    private boolean validateLogin(String loginText) {
        if (android.util.Patterns.EMAIL_ADDRESS.matcher(loginText).matches()){
            view.setLoginInputError(null);
            return true;
        }else {
            view.setLoginInputError("Invalid email");
            return false;
        }

    }

    private  boolean  valideateConfirmPassword(String confirmPassword, String password){
        if (state== AuthState.SIGNUP){
            if(!confirmPassword.equals(password)){
                view.setConfirmPasswordError("Passwords not match.");
                return false;
            }else {
                view.setConfirmPasswordError(null);
                return true;
            }
        }
        return true;
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
        navigator.navigateTo(Screen.MOVIE_SEARCH,ScreenType.ACTIVITY);
    }

    @Override
    public void login() {
      if(!validateLogin(view.getLoginText()) | !validatePassword(view.getPasswordText()) |
              !valideateConfirmPassword(view.getConfirmPasswordText(), view.getPasswordText()))return;
        Timber.d("Passed");
      if (state == AuthState.SIGNIN){
          mAuth.signInWithEmailAndPassword(view.getLoginText(),view.getPasswordText())
                  .addOnCompleteListener(task -> {
                      if (task.isSuccessful()){
                          navigateToHome();
                      }
                  }).addOnFailureListener(e -> {
              activity.showInfoDialog(e.getMessage());
          });

      }else {
          mAuth.createUserWithEmailAndPassword(view.getLoginText(),view.getPasswordText())
                  .addOnCompleteListener(task ->{
                        if (task.isSuccessful()){
                            navigateToHome();
                        }
                  }).addOnFailureListener(e -> {
              activity.showInfoDialog(e.getMessage());
          });
      }
    }

    private void navigateToHome() {
        navigator.navigateTo(Screen.MOVIE_SEARCH, ScreenType.ACTIVITY);
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
