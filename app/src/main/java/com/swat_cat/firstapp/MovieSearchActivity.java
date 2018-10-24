package com.swat_cat.firstapp;

import android.os.Bundle;

import com.swat_cat.firstapp.base.BaseActivity;
import com.swat_cat.firstapp.services.navigation.Screen;
import com.swat_cat.firstapp.services.navigation.ScreenType;

public class MovieSearchActivity extends BaseActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_search);
        getNavigator().navigateTo(Screen.MOVIE_SEARCH,ScreenType.FRAGMENT);
    }
}
