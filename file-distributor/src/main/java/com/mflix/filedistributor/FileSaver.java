package com.mflix.filedistributor;

import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.text.SimpleDateFormat;
import java.util.Date;

@Component
public class FileSaver {

    private final FilesDirectoryProperties filesDirectoryProperties;

    public FileSaver(FilesDirectoryProperties filesDirectoryProperties) {
        this.filesDirectoryProperties = filesDirectoryProperties;
    }

    public String save(File file) {
        String saveFileName = new SimpleDateFormat("yyMMdd-hhmmss.mp4").format(new Date());
        String saveFilePath = filesDirectoryProperties.getPath() + File.separator + saveFileName;
        try {
            Path savedPath = Files.copy(
                    file.toPath(),
                    Path.of(saveFilePath)
            );
            return savedPath.toString();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
