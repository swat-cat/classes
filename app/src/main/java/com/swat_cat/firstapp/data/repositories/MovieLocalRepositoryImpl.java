package com.swat_cat.firstapp.data.repositories;

import android.arch.persistence.room.Room;

import com.swat_cat.firstapp.base.App;
import com.swat_cat.firstapp.data.mappers.MovieEntetyMapper;
import com.swat_cat.firstapp.data.models.Movie;
import com.swat_cat.firstapp.data.models.MovieDetails;
import com.swat_cat.firstapp.services.rest.dto.SearchItemDTO;
import com.swat_cat.firstapp.services.room_db.MovieDB;
import com.swat_cat.firstapp.services.room_db.enteties.MovieEntity;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Flowable;
import io.reactivex.Maybe;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class MovieLocalRepositoryImpl implements MovieRepository {

    private MovieDB movieDB;
    private static final String DATABASE_NAME = "movies_db";
    private MovieEntetyMapper movieEntetyMapper;

    public MovieLocalRepositoryImpl() {
        this.movieDB = Room.databaseBuilder(App.getInstance().getApplicationContext(),
                MovieDB.class,DATABASE_NAME
        ).build();
        movieEntetyMapper = new MovieEntetyMapper();
    }

    @Override
    public Observable<List<Movie>> search(String query) {
        return Observable.just(new ArrayList<>());
    }

    @Override
    public Observable<MovieDetails> getMovieDetails(String id) {
        return null;
    }

    @Override
    public Flowable<List<Movie>> favourites() {
        return movieDB.daoAccess().getFavorites()
                .map(
                        it ->{
                            List<Movie> movies = new ArrayList<>();
                            for(MovieEntity entity: it){
                                movies.add(movieEntetyMapper.from(entity));
                            }
                            return movies;
                        }
                ).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public Completable saveMovie(Movie movie) {
        return Completable.fromAction(()->{
           movieDB.daoAccess().insertwSingleMovie(movieEntetyMapper.to(movie));
        }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }
}
