package com.mflix.admin_gateway.web;

import com.mflix.admin_gateway.api.Awards;
import com.mflix.admin_gateway.api.Imdb;
import com.mflix.admin_gateway.api.Tomatoes;

import java.util.Date;

public class AdminMoviePostRequest {

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
}
