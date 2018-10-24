package com.swat_cat.firstapp.services.rest;

import com.swat_cat.firstapp.services.rest.dto.SearchResultDTO;


import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface RestApi {

    @GET
    Observable<SearchResultDTO> search(@Query("type") String type, @Query("s")String s);
}
