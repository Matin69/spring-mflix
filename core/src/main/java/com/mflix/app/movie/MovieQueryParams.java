package com.mflix.app.movie;

import com.mflix.app.common.AbstractQueryParams;

public class MovieQueryParams extends AbstractQueryParams {

    private String title;

    private String year;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }
}
