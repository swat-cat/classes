package com.swat_cat.firstapp.movie;

public class MoviePresenter implements MovieContract.Presenter {

    MovieContract.View view;

    @Override
    public void start(MovieContract.View view) {
        this.view =view;
    }

    @Override
    public void stop() {

    }
}
