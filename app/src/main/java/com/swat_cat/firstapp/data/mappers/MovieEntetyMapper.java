package com.swat_cat.firstapp.data.mappers;

import com.swat_cat.firstapp.data.models.Movie;
import com.swat_cat.firstapp.services.room_db.enteties.MovieEntity;

public class MovieEntetyMapper implements Mapper<MovieEntity,Movie> {
    @Override
    public Movie from(MovieEntity data) {
        return new Movie(
                data.getType(),
                data.getYear(),
                data.getImdbID(),
                data.getPoster(),
                data.getTitle()
        );
    }

    @Override
    public MovieEntity to(Movie model) {
        MovieEntity entity = new MovieEntity();
        entity.setTitle(model.getTitle());
        entity.setImdbID(model.getImdbID());
        entity.setType(model.getType());
        entity.setPoster(model.getPoster());
        entity.setYear(model.getYear());
        return entity;
    }
}
