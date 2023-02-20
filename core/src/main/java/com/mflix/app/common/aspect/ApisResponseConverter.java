package com.mflix.app.common.aspect;

import com.mflix.app.common.Converter;
import com.mflix.app.common.ConverterRegistry;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

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
        if (content instanceof List) {
            List<Object> contentCollection = (List<Object>) content;
            Converter converter = converterRegistry.getConverter(contentCollection.get(0));
            List<Object> convertedEntities = contentCollection
                    .stream()
                    .map(converter::convert)
                    .collect(Collectors.toList());
            return ResponseEntity
                    .status(responseEntity.getStatusCode())
                    .body(convertedEntities);
        } else {
            Converter converter = converterRegistry.getConverter(content);
            Object convertedEntity = converter.convert(content);
            return ResponseEntity
                    .status(responseEntity.getStatusCode())
                    .body(convertedEntity);
        }
    }
}
