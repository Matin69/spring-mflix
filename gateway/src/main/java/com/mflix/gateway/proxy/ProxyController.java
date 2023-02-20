package com.mflix.gateway.proxy;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.support.StandardMultipartHttpServletRequest;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.IOException;
import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Map;

@RestController
public class ProxyController {

    private final RestTemplate restTemplate;

    private final RoutesProperties routesProperties;

    public ProxyController(RoutesProperties routesProperties) {
        this.routesProperties = routesProperties;
        this.restTemplate = new RestTemplate();
    }

    @RequestMapping(value = "/**")
    public Object proxy(HttpServletRequest request, @RequestHeader MultiValueMap<String, String> headers) {
        Object body = obtainBody(request);
        String uri = request.getRequestURI();
        String path = uri.substring(uri.indexOf("/", 1));
        HttpMethod method = HttpMethod.valueOf(request.getMethod());
        String queryParams = request.getQueryString();
        if (uri.startsWith("/core")) {
            return route(
                    routesProperties.getCoreUrl(),
                    path,
                    queryParams,
                    method,
                    headers,
                    body
            );
        } else if (uri.startsWith("/stream")) {
            return route(
                    routesProperties.getStreamUrl(),
                    path,
                    queryParams,
                    method,
                    headers,
                    body
            );
        } else {
            throw new RuntimeException();
        }
    }

    private Object obtainBody(HttpServletRequest request) {
        if (request.getContentType() == null) {
            return null;
        }
        String contentType = request.getContentType().split(";")[0];
        if (contentType.equals(MediaType.MULTIPART_FORM_DATA_VALUE)) {
            StandardMultipartHttpServletRequest multipartHttpServletRequest = (StandardMultipartHttpServletRequest) request;
            MultipartFile uploadedMultipartFile = multipartHttpServletRequest.getFile("movie_file");
            try {
                Path uploadedTempFile = Files.createTempFile(uploadedMultipartFile.getOriginalFilename(), null);
                uploadedMultipartFile.transferTo(uploadedTempFile);
                MultiValueMap<String, Object> proxyMultipartFormData = new LinkedMultiValueMap<>();
                proxyMultipartFormData.add("movie_file", uploadedTempFile);
                return proxyMultipartFormData;
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        } else {
            try {
                ObjectMapper objectMapper = new ObjectMapper();
                return objectMapper.readValue(request.getInputStream(), Map.class);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
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
