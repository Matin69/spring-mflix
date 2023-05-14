package com.mflix.admin_gateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class AdminMovieServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(AdminMovieServiceApplication.class, args);
    }
}
