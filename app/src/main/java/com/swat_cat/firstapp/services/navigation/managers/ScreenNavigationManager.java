package com.swat_cat.firstapp.services.navigation.managers;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import com.swat_cat.firstapp.R;
import com.swat_cat.firstapp.base.BaseActivity;
import com.swat_cat.firstapp.services.Navigator;
import com.swat_cat.firstapp.services.navigation.Screen;
import com.swat_cat.firstapp.services.navigation.ScreenAnimType;
import com.swat_cat.firstapp.services.navigation.ScreenType;
import com.swat_cat.firstapp.services.navigation.factories.ScreenActivityFactory;
import com.swat_cat.firstapp.services.navigation.factories.ScreenFragmentFactory;

public class ScreenNavigationManager implements Navigator {

    public final static String ACTIVITY_REQUEST_CODE = "ScreenNavigationManager.activityRequestCode";

    public final ScreenActivityFactory activityFactory;
    public final ScreenFragmentFactory fragmentFactory;
    public BaseActivity activity;

    public ScreenNavigationManager(BaseActivity activity) {
        this.activity = activity;
        activityFactory = new ScreenActivityFactory();
        fragmentFactory = new ScreenFragmentFactory();
    }

    @Override
    public void navigateTo(Screen screen, ScreenType type) {
        navigateTo(screen,type,null);
    }

    @Override
    public void navigateTo(Screen screen, ScreenType type, Bundle args) {
        switch (type) {
            case ACTIVITY:
                navigateToActivity(screen, args);
                break;
            case FRAGMENT:
                navigateToFragment(screen, args);
                break;
        }
    }

    private void navigateToFragment(Screen screen, Bundle args) {
        switch (screen) {
            case SHOPPING_LIST:
                navigateToShoppingList(args);
                break;
            case ITEM_DETAILS:
                navigateToItemDetails(args);
                break;

        }
    }

    private void navigateToActivity(Screen screen, Bundle args) {
        switch (screen) {
            case WELCOME:
                navigateToWelcome(args);
                break;
            case AUTH:
                navigateToAuth(args);
                break;
            case FORGOT_PASS:
                navigateToForgotPass(args);
                break;
            case SHOPPING:
                navigateToShopping(args);
                break;


        }
    }

    //Fragments
    private void navigateToItemDetails(Bundle args) {
        switchFragmentScreen(Screen.ITEM_DETAILS,args,ScreenAnimType.TOP_TO_BOTTOM_TYPE,true);
    }

    private void navigateToShoppingList(Bundle args) {
        switchFragmentScreen(Screen.SHOPPING_LIST,args,ScreenAnimType.NONE_TYPE,false);
    }



    //Activities
    private void navigateToShopping(Bundle args) {
        switchActivityScreen(Screen.SHOPPING,args,ScreenAnimType.FADE_TYPE,true);
        activity.hideKeyboard();
        activity.finish();
        activity.freeMemory();
    }

    private void navigateToForgotPass(Bundle args) {
        switchActivityScreen(Screen.FORGOT_PASS,args,ScreenAnimType.FADE_TYPE,false);
        activity.hideKeyboard();
    }

    private void navigateToAuth(Bundle args) {
        switchActivityScreen(Screen.AUTH,args,ScreenAnimType.FADE_TYPE,true);
        activity.hideKeyboard();
        activity.finish();
        activity.freeMemory();
    }

    private void navigateToWelcome(Bundle args) {
        switchActivityScreen(Screen.WELCOME,args,ScreenAnimType.NONE_TYPE,true);
        activity.hideKeyboard();
        activity.finish();
        activity.freeMemory();
    }

    private void switchActivityScreen(Screen type, Bundle bundle, ScreenAnimType animate, boolean clearStack) {
        Intent intent = activityFactory.getActivityByType(type);
        if (clearStack) {
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        } else {
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        }

        if (bundle != null && !bundle.isEmpty()) {
            intent.putExtras(bundle);
        }

        // logic for enabling handling result in onActivityResult
        int requestCode = 0;
        if (bundle != null) {
            requestCode = bundle.getInt(ACTIVITY_REQUEST_CODE, 0);
        }
        if (requestCode != 0) {
            activity.startActivityForResult(intent, requestCode);
        } else {
            activity.startActivity(intent);
        }


        switch (animate) {
            case NONE_TYPE:
                activity.overridePendingTransition(0, 0);
                break;
            case FADE_TYPE:
                activity.overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
                break;
            case BOTTOM_TO_TOP_TYPE:
                activity.overridePendingTransition(R.anim.slide_in_up, R.anim.slide_out_up);
                break;
            /*case RIGHT_TO_LEFT_TYPE:
                activity.overridePendingTransition(R.anim.right_to_left_in, R.anim.right_to_left_out);
                break;
            case LEFT_TO_RIGHT_TYPE:
                activity.overridePendingTransition(R.anim.left_to_right_in, R.anim.left_to_right_out);
                break;
            */
        }
    }

    private void switchFragmentScreen(Screen type, Bundle bundle,ScreenAnimType animate, boolean addToBackStack) {
        if (isSameFragmentAlreadyPlaced(type)) {
            return;
        }

        FragmentManager fragmentManager = activity.getSupportFragmentManager();
        FragmentTransaction tran = fragmentManager.beginTransaction();


        setAnimationForFragment(ScreenAnimType.BOTTOM_TO_TOP_TYPE, tran);



        Fragment fragment = fragmentFactory.getFragmentByType(type);
        if (bundle != null && !bundle.isEmpty()) {
            fragment.setArguments(bundle);
        }
        if (addToBackStack) {
           /* if (animate) {
                tran.setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out,android.R.anim.fade_in, android.R.anim.fade_out);
            }*/
            tran.replace(R.id.content_frame, fragment, fragment.getClass().getSimpleName());
            tran.addToBackStack(fragment.getClass().getSimpleName());
        } else {

            setAnimationForFragment(ScreenAnimType.FADE_TYPE,tran);

            tran.replace(R.id.content_frame, fragment);
        }
        tran.commit();
    }

    private FragmentTransaction setAnimationForFragment(ScreenAnimType animate,FragmentTransaction tran){

        switch (animate){
            case NONE_TYPE:
                tran.setCustomAnimations(0, 0);
                break;
            case FADE_TYPE:
                tran.setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out);
                break;
            case BOTTOM_TO_TOP_TYPE:
                tran.setCustomAnimations(R.anim.slide_in_up, R.anim.slide_out_up);
                break;
            case TOP_TO_BOTTOM_TYPE:
                tran.setCustomAnimations(R.anim.slide_out_up,R.anim.slide_in_up);
                break;
            /*case RIGHT_TO_LEFT_TYPE:
                activity.overridePendingTransition(R.anim.right_to_left_in, R.anim.right_to_left_out);
                break;
            case LEFT_TO_RIGHT_TYPE:
                activity.overridePendingTransition(R.anim.left_to_right_in, R.anim.left_to_right_out);
                break;
            */

        }
        return tran;

    }



    private boolean isSameFragmentAlreadyPlaced(Screen type) {
        Fragment existing = activity.getSupportFragmentManager().findFragmentById(R.id.content_frame);
        if (existing != null) {
            Class<? extends Fragment> requested = fragmentFactory.getFragmentClassByType(type);
            if (existing.getClass().equals(requested)) {
                return true;
            }
        }
        return false;
    }
}

