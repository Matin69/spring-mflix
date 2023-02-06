package com.mflix.filedistributor;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

@Component
public class ApiUrlBuilder {

    @Value("mflix.host")
    private String currentHost;

    @Value("mflix.scheme")
    private String currentScheme;

    public URI build(String uri) {
        return UriComponentsBuilder.newInstance()
                .scheme(currentScheme)
                .host(currentHost)
                .path(uri)
                .build()
                .toUri();
    }

    public String getCurrentHost() {
        return currentHost;
    }

    public void setCurrentHost(String currentHost) {
        this.currentHost = currentHost;
    }

    public String getCurrentScheme() {
        return currentScheme;
    }

    public void setCurrentScheme(String currentScheme) {
        this.currentScheme = currentScheme;
    }
}
