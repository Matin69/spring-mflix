package com.mflix.app.movie;

import com.mflix.app.common.Converter;

public class MovieConverter implements Converter {

    @Override
    public Object convert(Object obj) {
        Movie movie = (Movie) obj;
        return new MovieResponse(movie.id, movie.plot, movie.genres, movie.title, movie.fullPlot, movie.languages,
                movie.released, movie.directors, movie.rated, movie.awards, movie.year, movie.imdb, movie.countries,
                movie.type, movie.tomatoes);
    }

    @Override
    public boolean supports(Object obj) {
        return obj instanceof Movie;
    }
}
