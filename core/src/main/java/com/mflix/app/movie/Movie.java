package com.mflix.app.movie;

import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.FieldType;
import org.springframework.data.mongodb.core.mapping.MongoId;

import java.util.Date;

@Document("movies")
public class Movie {

    @MongoId(FieldType.OBJECT_ID)
    public String id;

    public String plot;

    public String[] genres;

    public String title;

    public String fullPlot;

    public String[] languages;

    public Date released;

    public String[] directors;

    public String rated;

    public Awards awards;

    public String year;

    public Imdb imdb;

    public String[] countries;

    public String type;

    public Tomatoes tomatoes;

    public Movie() {
    }

    public Movie(String plot, String[] genres, String title, String fullPlot, String[] languages, Date released, String[] directors, String rated, Awards awards, String year, Imdb imdb, String[] countries, String type, Tomatoes tomatoes) {
        this(null, plot, genres, title, fullPlot, languages, released, directors, rated, awards, year, imdb, countries, type, tomatoes);
    }

    public Movie(String id, String plot, String[] genres, String title, String fullPlot, String[] languages, Date released, String[] directors, String rated, Awards awards, String year, Imdb imdb, String[] countries, String type, Tomatoes tomatoes) {
        this.id = id;
        this.plot = plot;
        this.genres = genres;
        this.title = title;
        this.fullPlot = fullPlot;
        this.languages = languages;
        this.released = released;
        this.directors = directors;
        this.rated = rated;
        this.awards = awards;
        this.year = year;
        this.imdb = imdb;
        this.countries = countries;
        this.type = type;
        this.tomatoes = tomatoes;
    }
}
