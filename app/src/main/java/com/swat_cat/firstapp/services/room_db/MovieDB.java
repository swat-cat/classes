package com.swat_cat.firstapp.services.room_db;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

import com.swat_cat.firstapp.services.room_db.enteties.MovieEntity;

@Database(entities = {MovieEntity.class}, version = 1, exportSchema = false)
public abstract class MovieDB extends RoomDatabase {
    public abstract DaoAccess daoAccess();
}
