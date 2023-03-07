package com.mflix.gateway.proxy;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

@RestController
public class ProxyController {

    private final RestTemplate restTemplate;

    private final RoutesProperties routesProperties;

    public ProxyController(RoutesProperties routesProperties) {
        this.routesProperties = routesProperties;
        this.restTemplate = new RestTemplate();
    }

    @RequestMapping(value = "/**")
    public Object proxy(HttpServletRequest request, @RequestBody(required = false) Object body, @RequestHeader MultiValueMap<String, String> headers) {
        String uri = request.getRequestURI();
        HttpMethod method = HttpMethod.valueOf(request.getMethod());
        String queryParams = request.getQueryString();
        if (uri.startsWith("/movies")) {
            return route(
                    routesProperties.getMovieUrl(),
                    uri,
                    queryParams,
                    method,
                    headers,
                    body
            );
        } else if (uri.startsWith("/stream")) {
            return route(
                    routesProperties.getStreamUrl(),
                    uri,
                    queryParams,
                    method,
                    headers,
                    body
            );
        } else if (uri.startsWith("/comment")) {
            return route(
                    routesProperties.getCommentUrl(),
                    uri,
                    queryParams,
                    method,
                    headers,
                    body
            );
        } else {
            throw new RuntimeException();
        }
    }

    public Object route(String url, String path, String queryString, HttpMethod method, MultiValueMap<String, String> headers, Object body) {
        URI uri = UriComponentsBuilder.fromHttpUrl(url)
                .path(path)
                .query(queryString)
                .build()
                .toUri();
        return restTemplate.exchange(
                uri,
                method,
                new HttpEntity<>(body, headers),
                Object.class
        );
    }
}
