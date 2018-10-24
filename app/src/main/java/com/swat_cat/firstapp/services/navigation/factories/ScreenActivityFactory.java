package com.swat_cat.firstapp.services.navigation.factories;

import android.app.Activity;
import android.content.Intent;

import com.swat_cat.firstapp.ForgotPasswordActivity;
import com.swat_cat.firstapp.AuthActivity;
import com.swat_cat.firstapp.MovieSearchActivity;
import com.swat_cat.firstapp.ShoppingListActivity;
import com.swat_cat.firstapp.WelcomeActivity;
import com.swat_cat.firstapp.base.App;
import com.swat_cat.firstapp.services.navigation.Screen;

public class ScreenActivityFactory {

    public Intent getActivityByType(Screen screen){
        return new Intent(App.getInstance(),getActivityClassByType(screen));
    }

    private Class<? extends Activity> getActivityClassByType(Screen screen){
        switch (screen){
            case WELCOME:
                return WelcomeActivity.class;
            case AUTH:
                return AuthActivity.class;
            case SHOPPING:
                return ShoppingListActivity.class;
            case FORGOT_PASS:
                return ForgotPasswordActivity.class;
            case MOVIE_SEARCH:
                return MovieSearchActivity.class;

                default:return WelcomeActivity.class;
        }
    }

}
