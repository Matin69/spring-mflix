package com.mflix.app.common.aspect;

import com.mflix.app.common.Converter;
import com.mflix.app.common.ConverterRegistry;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class ApisResponseConverter {

    private final ConverterRegistry converterRegistry;

    public ApisResponseConverter(ConverterRegistry converterRegistry) {
        this.converterRegistry = converterRegistry;
    }

    public ResponseEntity<?> convert(ResponseEntity<?> responseEntity) {
        Object content = responseEntity.getBody();
        List<Object> contentCollection = new ArrayList<>();
        if (content instanceof List) {
            contentCollection = (List<Object>) content;
        } else {
            contentCollection.add(content);
        }
        if (contentCollection.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        Converter converter = converterRegistry.getConverter(contentCollection.get(0));
        List<Object> convertedEntities = contentCollection
                .stream()
                .map(converter::convert)
                .collect(Collectors.toList());
        return ResponseEntity
                .status(responseEntity.getStatusCode())
                .body(convertedEntities);
    }
}
