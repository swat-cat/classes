package com.swat_cat.firstapp.screens.movie_search;

import com.swat_cat.firstapp.services.rest.dto.SearchItemDTO;

import java.util.List;

import io.reactivex.Observable;

public interface MovieSearchContract {
    interface View{
        Observable<CharSequence> searchChanged();
        void setMovieList(List<SearchItemDTO> movies);
        void showLoading(boolean show);
        void showEmpty(boolean show);
        void showList(boolean show);
    }
    interface Presenter{
        void start(View view);
        void stop();
    }
}
