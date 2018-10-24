package com.swat_cat.firstapp.screens.movie_search;

import com.swat_cat.firstapp.data.repositories.MovieRepository;

import io.reactivex.disposables.CompositeDisposable;

public class MovieSearchPresenter implements MovieSearchContract.Presenter{

    private MovieSearchContract.View view;
    private MovieRepository repository;
    private CompositeDisposable subscriptions;

    @Override
    public void start(MovieSearchContract.View view) {
        this.view = view;
    }

    @Override
    public void stop() {
        view = null;
    }
}
