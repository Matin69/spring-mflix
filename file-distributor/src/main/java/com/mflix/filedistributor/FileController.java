package com.mflix.filedistributor;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/movies/file")
public class FileController {

    @GetMapping
    public void download() {

    }

    @PostMapping
    public void upload() {

    }
}
