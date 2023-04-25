package com.mflix.movie;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class MovieCompositionServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(MovieCompositionServiceApplication.class, args);
    }
}
