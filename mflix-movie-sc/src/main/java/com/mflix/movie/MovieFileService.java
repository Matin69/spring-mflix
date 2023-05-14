package com.mflix.movie;

import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

@Component
public class MovieFileService {

    private final MovieFileRepositoryProperties movieFileRepositoryProperties;

    public MovieFileService(MovieFileRepositoryProperties movieFileRepositoryProperties) {
        this.movieFileRepositoryProperties = movieFileRepositoryProperties;
    }

    public String save(MultipartFile movieFilePart) {
        String currentTime = new SimpleDateFormat("yyMMdd-hhmmss").format(new Date());
        String saveFileName = String.format("%s.mp4", currentTime);
        String saveFilePath = movieFileRepositoryProperties.getDirPath() + File.separator + saveFileName;
        try {
            movieFilePart.transferTo(new File(saveFilePath));
            return saveFilePath;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
