package com.swat_cat.firstapp.screens.shopping_list;

import android.view.View;

import com.squareup.otto.Subscribe;
import com.swat_cat.firstapp.R;
import com.swat_cat.firstapp.base.BaseActivity;
import com.swat_cat.firstapp.base.action_bar.ActionBarContract;
import com.swat_cat.firstapp.services.navigation.managers.events.BackPressEvent;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

public class ShoppingListActionBarPresenter implements ActionBarContract.Presenter {

    private BaseActivity activity;
    private ActionBarContract.View view;
    private CompositeDisposable disposable;

    public ShoppingListActionBarPresenter(BaseActivity activity, ActionBarContract.View view) {
        this.activity = activity;
        this.view = view;
        disposable = new CompositeDisposable();
    }

    @Override
    public void setupView() {
        View leftIcon  = activity.getLayoutInflater().inflate(R.layout.ab_back,null);
        view.setupLeftButton(leftIcon);
        view.setupCenterText(R.string.shopping_list);
    }

    @Override
    public void setupActions() {
        view.leftButtonAction().subscribe(
                o -> leftButtonAction()
        );
    }

    @Override
    public void leftButtonAction() {
        activity.getBus().post(new BackPressEvent());
    }

    @Override
    public void rightButtonAction() {
    }

    @Override
    public void dispose() {
        if(disposable!=null){
            disposable.dispose();
        }
    }

    @Override
    public void start() {
        setupView();
        setupActions();
    }

    @Override
    public void stop() {
        dispose();
    }
}
