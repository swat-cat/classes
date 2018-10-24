package com.swat_cat.firstapp.data.repositories;

import com.swat_cat.firstapp.services.rest.RestApi;
import com.swat_cat.firstapp.services.rest.RestClient;
import com.swat_cat.firstapp.services.rest.dto.SearchResultDTO;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class MovieRepositoryImpl implements MovieRepository{

    private RestApi restApi;

    public MovieRepositoryImpl() {
        restApi = RestClient.createApi();
    }

    @Override
    public Observable<SearchResultDTO> search(String query) {
        return restApi.search("movie",query)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }
}
