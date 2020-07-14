package com.seaky.fch.open.api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.io.IOException;

@SpringBootApplication
public class App {

    public static void main(String[] args) throws IOException, InterruptedException {
        SpringApplication.run(App.class, args);
    }
}
