package com.swat_cat.firstapp.data.mappers;

import com.swat_cat.firstapp.data.models.MovieDetails;
import com.swat_cat.firstapp.services.rest.dto.MovieDetailsDTO;

import java.util.ArrayList;

public class MovieDetailsMapper implements Mapper<MovieDetailsDTO, MovieDetails> {

    @Override
    public MovieDetails from(MovieDetailsDTO data) {
       MovieDetails movieDetails =  new MovieDetails(
                data.getImdbID(),
                data.getTitle(),
                data.getPoster(),
                new ArrayList<>(),
                data.getPlot(),
                0.0
        );
        if (data.getRatings()!=null && !data.getRatings().isEmpty()) {
            movieDetails.transformRaitingToDouble(data.getRatings().get(0).getValue());
        }
        if (data.getGenre()!=null && !data.getGenre().isEmpty()){
            movieDetails.transformListOfGanres(data.getGenre());
        }
        return  movieDetails;
    }

    @Override
    public MovieDetailsDTO to(MovieDetails model) {
        return null;
    }
}
