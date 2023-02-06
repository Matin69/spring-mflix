package com.mflix.filedistributor;

import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

@Component
public class ApiUrlBuilder {

    private final MflixProperties mflixProperties;

    public ApiUrlBuilder(MflixProperties mflixProperties) {
        this.mflixProperties = mflixProperties;
    }

    public URI build(String uri) {
        return UriComponentsBuilder.newInstance()
                .scheme(mflixProperties.getScheme())
                .host(mflixProperties.getHost())
                .path(uri)
                .build()
                .toUri();
    }
}
