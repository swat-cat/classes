package com.swat_cat.firstapp.login;

import android.support.constraint.ConstraintLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.jakewharton.rxbinding2.view.RxView;
import com.jakewharton.rxbinding2.widget.RxTextView;
import com.squareup.otto.Bus;
import com.swat_cat.firstapp.R;
import com.swat_cat.firstapp.base.dialogs.events.HideDialogEvent;
import com.swat_cat.firstapp.base.dialogs.events.ShowDialogEvent;

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
    private TextView testText;
    public String email;

    private Bus bus;

    public LoginView(View root) {
        this.root = root;
        initView();
    }

    public void initView() {
        content = root.findViewById(R.id.content_frame);
        progress = root.findViewById(R.id.progress);
        loginInput = root.findViewById(R.id.login);
        passwordInput = root.findViewById(R.id.password);
        confirmBtn = root.findViewById(R.id.confirm_btn);
        forgotPassword = root.findViewById(R.id.forgot_password);
        testText = root.findViewById(R.id.test_text);
        if (email!=null) {
            testText.setText(email);
        }
    }

    @Override
    public void setLoginText(String text) {
        loginInput.setText(text);
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
    public void resizeButtonAndChangeMarging() {
        ConstraintLayout.LayoutParams layoutParams = (ConstraintLayout.LayoutParams) confirmBtn.getLayoutParams();
        layoutParams.height = layoutParams.height*2;
        layoutParams.width = layoutParams.width*2;
        layoutParams.topMargin = layoutParams.topMargin+150;
        confirmBtn.setLayoutParams(layoutParams);
    }

    @Override
    public void showLoading(boolean show) {
        progress.setVisibility(show?View.VISIBLE:View.GONE);
        content.setVisibility(show?View.GONE:View.VISIBLE);
    }

    @Override
    public void showMessage(String message) {
        View view = LayoutInflater.from(root.getContext()).inflate(R.layout.info_dialog,null);
        TextView text = view.findViewById(R.id.message);
        text.setText(message);
        View okButton = view.findViewById(R.id.ok);
        okButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bus.post(new HideDialogEvent());
            }
        });
        bus.post(new ShowDialogEvent(view));
    }

    public void setBus(Bus bus) {
        this.bus = bus;
    }
}
