package com.mflix.stream;

import org.springframework.stereotype.Component;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Date;

@Component
public class FileIoUtils {

    private final FilesDirectoryProperties filesDirectoryProperties;

    public FileIoUtils(FilesDirectoryProperties filesDirectoryProperties) {
        this.filesDirectoryProperties = filesDirectoryProperties;
    }

    public String save(MultipartFile multipartFile) {
        String currentTime = new SimpleDateFormat("yyMMdd-hhmmss").format(new Date());
        String saveFileName = String.format("%s.mp4", currentTime);
        String saveFilePath = filesDirectoryProperties.getPath() + File.separator + saveFileName;
        try {
            multipartFile.transferTo(new File(saveFilePath));
            return saveFilePath;
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
