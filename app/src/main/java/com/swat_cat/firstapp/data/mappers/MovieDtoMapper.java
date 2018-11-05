package com.swat_cat.firstapp.data.mappers;

import com.swat_cat.firstapp.data.models.Movie;
import com.swat_cat.firstapp.services.rest.dto.SearchItemDTO;

public class MovieDtoMapper implements Mapper<SearchItemDTO,Movie> {
    @Override
    public Movie from(SearchItemDTO data) {
        return new Movie(
                data.getType(),
                data.getYear(),
                data.getImdbID(),
                data.getPoster(),
                data.getTitle()
        );
    }

    @Override
    public SearchItemDTO to(Movie model) {
        return null;
    }
}
