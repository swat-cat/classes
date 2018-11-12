package com.swat_cat.firstapp.services.rest;

import com.swat_cat.firstapp.services.rest.dto.MovieDetailsDTO;
import com.swat_cat.firstapp.services.rest.dto.SearchResultDTO;


import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface RestApi {

    @GET("?apikey=33d3ea25")
    Observable<SearchResultDTO> search(@Query("type") String type, @Query("s")String s);

    @GET("?apikey=33d3ea25")
    Observable<MovieDetailsDTO> movieDetails(@Query("i") String id);
}
