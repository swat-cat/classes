package com.swat_cat.firstapp.data.repositories;

import com.swat_cat.firstapp.data.mappers.MovieDetailsMapper;
import com.swat_cat.firstapp.data.mappers.MovieDtoMapper;
import com.swat_cat.firstapp.data.models.Movie;
import com.swat_cat.firstapp.data.models.MovieDetails;
import com.swat_cat.firstapp.services.rest.RestApi;
import com.swat_cat.firstapp.services.rest.RestClient;
import com.swat_cat.firstapp.services.rest.dto.SearchItemDTO;
import com.swat_cat.firstapp.services.rest.dto.SearchResultDTO;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Flowable;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class MovieNetworkRepositoryImpl implements MovieRepository{

    private RestApi restApi;
    private MovieDtoMapper movieDtoMapper;
    private MovieDetailsMapper movieDetailsMapper;

    public MovieNetworkRepositoryImpl() {
        restApi = RestClient.createApi();
        movieDtoMapper = new MovieDtoMapper();
        movieDetailsMapper = new MovieDetailsMapper();
    }

    @Override
    public Observable<List<Movie>> search(String query) {
        return restApi.search("movie",query)
                .map(
                        it->{
                            List<Movie> movies = new ArrayList<>();
                            if (it.getSearch()!=null) {
                                for(SearchItemDTO itemDTO: it.getSearch()){
                                    movies.add(movieDtoMapper.from(itemDTO));
                                }
                            }
                            return movies;
                        }
                )
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public Observable<MovieDetails> getMovieDetails(String id) {
        return restApi.movieDetails(id)
                .map(it-> movieDetailsMapper.from(it))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public Flowable<List<Movie>> favourites() {
        return Flowable.just(new ArrayList<>());
    }

    @Override
    public Completable saveMovie(Movie movie) {
        return Completable.complete();
    }
}