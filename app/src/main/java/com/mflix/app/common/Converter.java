package com.mflix.app.common;

public interface Converter {

    Object convert(Object obj);

    boolean supports(Object obj);
}
