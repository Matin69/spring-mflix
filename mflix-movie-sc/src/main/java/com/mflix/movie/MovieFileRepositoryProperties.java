package com.mflix.movie;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties("mflix.movie.file.repository.dir.path")
public class MovieFileRepositoryProperties {

    private final String dirPath;

    public MovieFileRepositoryProperties(String dirPath) {
        this.dirPath = dirPath;
    }

    public String getDirPath() {
        return dirPath;
    }
}
