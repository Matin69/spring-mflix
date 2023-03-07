package com.mflix.core.common;

public interface Converter {

    Object convert(Object obj);

    boolean supports(Object obj);
}
