package com.swat_cat.firstapp.data.repositories;

import com.swat_cat.firstapp.services.rest.dto.SearchResultDTO;

import io.reactivex.Observable;


public interface MovieRepository {
    Observable<SearchResultDTO> search(String query);
}
