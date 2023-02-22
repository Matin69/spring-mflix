package com.mflix.core.movie;

import com.mflix.annotation.ResponseConverter;
import com.mflix.core.common.Converter;

@ResponseConverter
public class MovieConverter implements Converter {

    @Override
    public Object convert(Object obj) {
        Movie movie = (Movie) obj;
        return new MovieResponse(movie.id, movie.plot, movie.genres, movie.title, movie.fullPlot, movie.languages,
                movie.released, movie.directors, movie.rated, movie.awards, movie.year, movie.imdb, movie.countries,
                movie.type, movie.tomatoes, movie.filePath);
    }

    @Override
    public boolean supports(Object obj) {
        return obj instanceof Movie;
    }
}
