package com.swat_cat.firstapp;

import android.os.Bundle;

import com.swat_cat.firstapp.base.BaseActivity;
import com.swat_cat.firstapp.screens.movie_search.MovieSearchContract;
import com.swat_cat.firstapp.screens.movie_search.MovieSearchPresenter;
import com.swat_cat.firstapp.screens.movie_search.MovieSearchView;

public class MovieSearchActivity extends BaseActivity {

    private MovieSearchContract.View view;
    private MovieSearchContract.Presenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_search);
        view = new MovieSearchView(findViewById(R.id.content_frame));
        presenter = new MovieSearchPresenter();
    }

    @Override
    protected void onStart() {
        super.onStart();
        presenter.start(view);
    }

    @Override
    protected void onStop() {
        super.onStop();
        presenter.stop();
    }
}
