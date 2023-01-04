package com.mflix.app.common;

import java.util.ArrayList;
import java.util.List;

public class ConverterRegistry {

    private final List<Converter> converters = new ArrayList<>();

    public void addConverter(Converter converter) {
        converters.add(converter);
    }

    public Converter getConverter(Object obj) {
        return converters.stream()
                .filter(converter -> converter.supports(obj))
                .findFirst()
                .orElseThrow(RuntimeException::new);
    }
}
