package com.swat_cat.firstapp.login;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;

import com.jakewharton.rxbinding2.view.RxView;
import com.jakewharton.rxbinding2.widget.RxTextView;
import com.swat_cat.firstapp.R;

import io.reactivex.Observable;

/**
 * Created by max_ermakov on 9/26/18.
 */

public class LoginView implements LoginContract.View {

    private View root;
    private View content;
    private View progress;

    private EditText loginInput;
    private EditText passwordInput;
    private View confirmBtn;
    private View forgotPassword;


    public LoginView(View root) {
        this.root = root;
        initView();
    }

    private void initView() {
        content = root.findViewById(R.id.content);
        progress = root.findViewById(R.id.progress);
        loginInput = root.findViewById(R.id.login);
        passwordInput = root.findViewById(R.id.password);
        confirmBtn = root.findViewById(R.id.confirm_btn);
        forgotPassword = root.findViewById(R.id.forgot_password);
    }

    @Override
    public Observable<CharSequence> loginInputChanged() {
        return RxTextView.textChanges(loginInput);
    }

    @Override
    public String getLoginText() {
        return loginInput.getText().toString().trim();
    }

    @Override
    public void setLoginInputError(String error) {
        loginInput.setError(error);
    }

    @Override
    public Observable<CharSequence> passwordInputChanged() {
        return RxTextView.textChanges(passwordInput);
    }

    @Override
    public String getPasswordText() {
        return passwordInput.getText().toString().trim();
    }

    @Override
    public void setPasswordInputError(String error) {
        passwordInput.setError(error);
    }

    @Override
    public Observable<Object> confirmBtnAction() {
        return RxView.clicks(confirmBtn);
    }

    @Override
    public Observable<Object> forgotPasswordAction() {
        return RxView.clicks(forgotPassword);
    }

    @Override
    public void showLoading(boolean show) {
        progress.setVisibility(show?View.VISIBLE:View.GONE);
        content.setVisibility(show?View.GONE:View.VISIBLE);
    }
}
