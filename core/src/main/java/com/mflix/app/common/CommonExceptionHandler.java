package com.mflix.app.common;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class CommonExceptionHandler {

    @ExceptionHandler(WebException.class)
    public ResponseEntity<?> handleRestException(WebException e) {
        WebExceptionResponse exceptionResponse = new WebExceptionResponse(
                new WebExceptionDetail(
                        e.message,
                        e.status,
                        e.code
                )
        );
        return ResponseEntity
                .status(e.status)
                .body(exceptionResponse);
    }

    private static class WebExceptionResponse {

        public WebExceptionDetail error;

        public WebExceptionResponse(WebExceptionDetail error) {
            this.error = error;
        }
    }

    private static class WebExceptionDetail {

        public String message;

        public int status;

        public String code;

        public WebExceptionDetail(String message, int status, String code) {
            this.message = message;
            this.status = status;
            this.code = code;
        }
    }
}
