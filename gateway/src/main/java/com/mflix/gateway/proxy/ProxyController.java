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

import java.net.URI;
import java.net.URISyntaxException;

@RestController
public class ProxyController {

    private final RestTemplate restTemplate;

    public ProxyController() {
        this.restTemplate = new RestTemplate();
    }

    @RequestMapping("/**")
    public Object proxy(HttpServletRequest request, @RequestBody(required = false) Object body, @RequestHeader MultiValueMap<String, String> headers) {
        String uri = request.getRequestURI();
        HttpMethod method = HttpMethod.valueOf(request.getMethod());
        String queryParams = request.getQueryString();
        if (uri.startsWith("/core")) {
            return route(
                    uri,
                    8081,
                    queryParams,
                    method,
                    headers,
                    body
            );
        } else if (uri.startsWith("/stream")) {
            return route(
                    uri,
                    8082,
                    queryParams,
                    method,
                    headers,
                    body
            );
        } else {
            throw new RuntimeException();
        }
    }

    public Object route(String path, int port, String queryString, HttpMethod method, MultiValueMap<String, String> headers, Object body) {
        try {
            URI uri = new URI("http", null, "localhost", port, path, queryString, null);
            return restTemplate.exchange(
                    uri,
                    method,
                    new HttpEntity<>(body, headers),
                    Object.class
            );
        } catch (URISyntaxException e) {
            e.printStackTrace();
            throw new RuntimeException();
        }
    }
}
