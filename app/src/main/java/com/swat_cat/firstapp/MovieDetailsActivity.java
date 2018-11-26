package com.swat_cat.firstapp;

import android.os.Bundle;

import com.swat_cat.firstapp.base.BaseActivity;
import com.swat_cat.firstapp.base.action_bar.ActionBarContract;
import com.swat_cat.firstapp.databinding.FragmentMovieDetailsBinding;
import com.swat_cat.firstapp.screens.movie_details.MovieDetailsViewModel;
import com.swat_cat.firstapp.services.mvvm.activities.BindingActivity;

import static com.swat_cat.firstapp.utils.Constants.MOVIE_ID;

public class MovieDetailsActivity extends BindingActivity<FragmentMovieDetailsBinding,MovieDetailsViewModel> {


    MovieDetailsViewModel viewModel;

    @Override
    public MovieDetailsViewModel onCreate() {
        String movieId = "";
        Bundle args = getIntent().getExtras();
        if(args!=null && args.containsKey(MOVIE_ID)){
            movieId = args.getString(MOVIE_ID,"");
        }
       viewModel =  new MovieDetailsViewModel(this,movieId);
       return viewModel;
    }

    @Override
    public int getVariable() {
        return BR.vm;
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_movie_details;
    }

    @Override
    public ActionBarContract.View getActionBarView() {
        return null;
    }
}
