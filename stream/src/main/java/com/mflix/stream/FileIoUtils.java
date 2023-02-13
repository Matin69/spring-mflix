package com.mflix.stream;

import org.springframework.stereotype.Component;
import org.springframework.util.FileCopyUtils;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.text.SimpleDateFormat;
import java.util.Date;

@Component
public class FileIoUtils {

    private final FilesDirectoryProperties filesDirectoryProperties;

    public FileIoUtils(FilesDirectoryProperties filesDirectoryProperties) {
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

    public void stream(String inputFilePath, OutputStream outputStream) {
        try (
                BufferedInputStream bufferedInputStream = new BufferedInputStream(
                        new FileInputStream(inputFilePath)
                );
                BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(
                        outputStream
                )
        ) {
            FileCopyUtils.copy(bufferedInputStream, bufferedOutputStream);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public String getPathFileName(String path) {
        return new File(path).getName();
    }
}
