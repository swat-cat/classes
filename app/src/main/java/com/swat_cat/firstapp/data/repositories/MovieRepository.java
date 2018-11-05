package com.swat_cat.firstapp.data.repositories;

import com.swat_cat.firstapp.data.models.Movie;
import com.swat_cat.firstapp.services.rest.dto.SearchResultDTO;

import java.util.List;

import io.reactivex.Observable;


public interface MovieRepository {
    Observable<List<Movie>> search(String query);
    Observable<List<Movie>> favourites();
    Observable<Long> saveMovie(Movie movie);
}
