package com.mflix.filedistributor;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class FileDistributorApplication {

    public static void main(String[] args) {
        SpringApplication.run(FileDistributorApplication.class, args);
    }

}
