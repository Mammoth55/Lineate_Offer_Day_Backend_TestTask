package com.example.offerdaysongs;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.servers.Server;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@OpenAPIDefinition(
//        servers = {@Server(url = "https:/"), @Server(url = "http:/")},
        info = @Info(title = "OfferDay Songs API", version = "1.0", description = "OfferDay Songs API"))
public class OfferDaySongsApplication {

    public static void main(String[] args) {
        SpringApplication.run(OfferDaySongsApplication.class, args);
    }
}