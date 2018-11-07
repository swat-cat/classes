package com.swat_cat.firstapp.data.repositories;

import com.swat_cat.firstapp.data.mappers.Mapper;
import com.swat_cat.firstapp.data.models.Movie;
import com.swat_cat.firstapp.services.rest.dto.SearchResultDTO;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Flowable;
import io.reactivex.Maybe;
import io.reactivex.Observable;


public interface MovieRepository {
    Observable<List<Movie>> search(String query);
    Flowable<List<Movie>> favourites();
    Completable saveMovie(Movie movie);
}
