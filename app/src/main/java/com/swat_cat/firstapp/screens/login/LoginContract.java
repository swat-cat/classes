package com.swat_cat.firstapp.screens.login;

import com.swat_cat.firstapp.services.Navigator;

import io.reactivex.Observable;
import io.reactivex.annotations.Nullable;

/**
 * Created by max_ermakov on 9/26/18.
 */

public interface LoginContract {
    interface View{
        void showLoading(boolean show);
        Observable<CharSequence> loginInputChanged();
        String getLoginText();
        void setLoginInputError(@Nullable String error);
        Observable<CharSequence> passwordInputChanged();
        Observable<CharSequence> confirmPasswordChanged();
        String getPasswordText();
        void setPasswordInputError(@Nullable String error);
        void setConfirmPasswordError(@Nullable String error);
        String getConfirmPasswordText();
        Observable<Object> confirmBtnAction();
        Observable<Object> forgotPasswordAction();
        void resizeButtonAndChangeMarging();
        void showMessage(String message);
        void setLoginText(String text);
        void changeAuthState(AuthState state);
    }
    interface Presenter{
        void start(View view);
        void stop();
        void login();
        void forgotPassword();
        void setNavigator(Navigator navigator);
        String getEmail();
    }
}


enum AuthState{
    SIGNIN, SIGNUP
}