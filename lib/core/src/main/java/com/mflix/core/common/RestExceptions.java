package com.mflix.core.common;

import org.springframework.http.HttpStatus;

public class RestExceptions {

    public static final WebException resourceNotFoundException = new WebException(
            "Resource not found",
            HttpStatus.NOT_FOUND.value(),
            "E-100"
    );
}
