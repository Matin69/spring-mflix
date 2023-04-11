package com.mflix.gateway.proxy;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class RoutesProperties {

    @Value("${mflix.apis.movie.url}")
    private String movieUrl;

    @Value("${mflix.apis.comment.url}")
    private String commentUrl;

    @Value("${mflix.apis.stream.url}")
    private String streamUrl;

    @Value("${mflix.apis.auth.url}")
    private String authUrl;

    @Value("${mflix.apis.user.url}")
    private String userUrl;

    public String getMovieUrl() {
        return movieUrl;
    }

    public void setMovieUrl(String movieUrl) {
        this.movieUrl = movieUrl;
    }

    public String getStreamUrl() {
        return streamUrl;
    }

    public void setStreamUrl(String streamUrl) {
        this.streamUrl = streamUrl;
    }

    public String getCommentUrl() {
        return commentUrl;
    }

    public void setCommentUrl(String commentUrl) {
        this.commentUrl = commentUrl;
    }

    public String getAuthUrl() {
        return authUrl;
    }

    public void setAuthUrl(String authUrl) {
        this.authUrl = authUrl;
    }

    public String getUserUrl() {
        return userUrl;
    }

    public void setUserUrl(String userUrl) {
        this.userUrl = userUrl;
    }
}
