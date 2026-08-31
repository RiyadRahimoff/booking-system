package com.bookflow;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
public class BookflowApplication {

    public static void main(String[] args) {
        SpringApplication.run(BookflowApplication.class, args);
    }

}
