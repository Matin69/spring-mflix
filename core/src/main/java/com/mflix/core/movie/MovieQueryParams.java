package com.mflix.core.movie;

import com.mflix.core.common.AbstractQueryParams;

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
