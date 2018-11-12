package com.swat_cat.firstapp;

import android.os.Bundle;

import com.swat_cat.firstapp.base.BaseActivity;

public class TestActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_movie_details);
    }
}
