package com.swat_cat.firstapp.services.room_db;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import com.swat_cat.firstapp.services.room_db.enteties.MovieEntity;

import java.util.List;

import io.reactivex.Maybe;
import io.reactivex.Observable;
import io.reactivex.Single;

@Dao
public interface DaoAccess {
    @Insert
    Observable<Long> insertSingleMovie(MovieEntity movieEntity);

    @Query("SELECT * FROM MovieEntity")
    Observable<List<MovieEntity>> getFavorites();
}
