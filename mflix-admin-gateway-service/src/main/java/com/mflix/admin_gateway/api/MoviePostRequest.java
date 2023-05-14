package com.mflix.admin_gateway.api;

import java.util.Date;

public class MoviePostRequest {

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

    public MoviePostRequest() {
    }

    public MoviePostRequest(String plot, String[] genres, String title, String fullPlot, String[] languages, Date released, String[] directors, String rated, Awards awards, String year, Imdb imdb, String[] countries, String type, Tomatoes tomatoes) {
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
