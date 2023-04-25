package com.mflix.domain_gateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class SpaGatewayServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpaGatewayServiceApplication.class, args);
    }
}
