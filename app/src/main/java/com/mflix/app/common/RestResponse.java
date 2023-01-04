package com.mflix.app.common;

public class RestResponse<T> {

    public boolean acknowledged;

    public String action;

    public T source;

    public RestResponse(boolean acknowledged, String action, T source) {
        this.acknowledged = acknowledged;
        this.action = action;
        this.source = source;
    }
}
