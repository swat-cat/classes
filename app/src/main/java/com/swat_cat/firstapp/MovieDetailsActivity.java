package com.swat_cat.firstapp;

import android.os.Bundle;

import com.swat_cat.firstapp.base.BaseActivity;
import com.swat_cat.firstapp.databinding.FragmentMovieDetailsBinding;
import com.swat_cat.firstapp.screens.movie_details.MovieDetailsViewModel;
import com.swat_cat.firstapp.services.mvvm.activities.BindingActivity;

public class MovieDetailsActivity extends BindingActivity<FragmentMovieDetailsBinding,MovieDetailsViewModel> {

    @Override
    public MovieDetailsViewModel onCreate() {
        return null;
    }

    @Override
    public int getVariable() {
        return 0;
    }

    @Override
    public int getLayoutId() {
        return 0;
    }
}
