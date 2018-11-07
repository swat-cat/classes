package com.swat_cat.firstapp.services.room_db;



import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import com.swat_cat.firstapp.services.room_db.enteties.MovieEntity;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Flowable;
import io.reactivex.Maybe;
import io.reactivex.Observable;
import io.reactivex.Single;

@Dao
public interface DaoAccess {
    @Insert
    public Long insertwSingleMovie(MovieEntity movieEntity);

    @Query("SELECT * FROM MovieEntity")
    public abstract Flowable<List<MovieEntity>> getFavorites();
}
