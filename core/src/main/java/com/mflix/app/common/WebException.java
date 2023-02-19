package com.mflix.app.common;

public class WebException extends RuntimeException {

    public String message;

    public int status;

    public String code;

    public WebException(String message, int status, String code) {
        this.message = message;
        this.status = status;
        this.code = code;
    }
}
