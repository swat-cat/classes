package com.swat_cat.firstapp.services.navigation.managers;

import android.os.Handler;
import android.os.Looper;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.View;

import com.squareup.otto.Subscribe;
import com.swat_cat.firstapp.R;
import com.swat_cat.firstapp.base.BaseActivity;
import com.swat_cat.firstapp.services.navigation.managers.events.BackPressEvent;
import com.swat_cat.firstapp.services.navigation.managers.events.TryExitActivityEvent;
import com.swat_cat.firstapp.services.navigation.managers.events.TryNavigateBackEvent;

import timber.log.Timber;

public class ScreenNavigationBackManager {

    private BaseActivity activity;
    private boolean couldNavigateBack = true;
    private boolean doubleBackToExitPressedOnce = false;
    private static final int TIME_OUT = 2000;
    private Handler handler;

    public ScreenNavigationBackManager(BaseActivity activity) {
        this.activity = activity;
        handler = new Handler(Looper.getMainLooper());
    }

    public boolean isCouldNavigateBack() {
        return couldNavigateBack;
    }

    public void setCouldNavigateBack(boolean couldNavigateBack) {
        this.couldNavigateBack = couldNavigateBack;
    }

    private void navigateBack(){
        FragmentManager fragmentManager = activity.getSupportFragmentManager();
        if (fragmentManager.getBackStackEntryCount() > 0) {
            Timber.d("pop fragment from backstack");

            FragmentManager.BackStackEntry backEntry = fragmentManager.getBackStackEntryAt(fragmentManager.getBackStackEntryCount() - 1);
            String fragmentName = backEntry.getName();
            fragmentManager.popBackStackImmediate(fragmentName, FragmentManager.POP_BACK_STACK_INCLUSIVE);
        } else {
            tryExitActivity();
        }
    }

    private void tryExitActivity() {
        activity.hideKeyboard();
        if (activity.isTaskRoot()) {
            if (doubleBackToExitPressedOnce){
                activity.finish();
                activity.freeMemory();
            }else {
                doubleBackToExitPressedOnce = true;
                Snackbar.make(activity.findViewById(R.id.content_frame), R.string.back_message, Snackbar.LENGTH_LONG)
                        .show();
                handler.postDelayed(() -> doubleBackToExitPressedOnce = false,TIME_OUT);
            }
        }else {
            activity.finish();
            activity.freeMemory();
        }
    }

    @Subscribe
    public void onEvent(BackPressEvent event){
        if (couldNavigateBack){
            navigateBack();
        }else {
            activity.getBus().post(new TryNavigateBackEvent());
        }
    }

    @Subscribe
    public void onEvent(TryExitActivityEvent event){
        tryExitActivity();
    }
}
