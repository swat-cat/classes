package com.swat_cat.firstapp.data.repositories;

import com.swat_cat.firstapp.data.mappers.MovieDtoMapper;
import com.swat_cat.firstapp.data.models.Movie;
import com.swat_cat.firstapp.services.rest.RestApi;
import com.swat_cat.firstapp.services.rest.RestClient;
import com.swat_cat.firstapp.services.rest.dto.SearchItemDTO;
import com.swat_cat.firstapp.services.rest.dto.SearchResultDTO;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class MovieNetworkRepositoryImpl implements MovieRepository{

    private RestApi restApi;
    private MovieDtoMapper movieDtoMapper;

    public MovieNetworkRepositoryImpl() {
        restApi = RestClient.createApi();
        movieDtoMapper = new MovieDtoMapper();
    }

    @Override
    public Observable<List<Movie>> search(String query) {
        return restApi.search("movie",query)
                .map(
                        it->{
                            List<Movie> movies = new ArrayList<>();
                            for(SearchItemDTO itemDTO: it.getSearch()){
                                movies.add(movieDtoMapper.from(itemDTO));
                            }
                            return movies;
                        }
                )
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public Observable<List<Movie>> favourites() {
        return Observable.just(new ArrayList<>());
    }

    @Override
    public Observable<Long> saveMovie(Movie movie) {
        return Observable.just(0L);
    }
}
