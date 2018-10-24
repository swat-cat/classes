package com.swat_cat.firstapp.movie;

import com.swat_cat.firstapp.login.LoginContract;
import com.swat_cat.firstapp.services.Navigator;

import java.io.File;

import io.reactivex.Observable;
import io.reactivex.annotations.Nullable;

public interface MovieContract {
    interface View{
        void showNoResult(boolean show);
        void showLoading(boolean show);
        void setMovieList(File file);
        Observable<CharSequence> searchInputChanged();
        String getSearchText();
        void setSearchInputError(@Nullable String error);
        Observable<Object> searchBtnAction();
        Observable<Object> deleteBtnAction();
    }

    interface Presenter{
        void start(MovieContract.View view);
        void stop();

    }
}
