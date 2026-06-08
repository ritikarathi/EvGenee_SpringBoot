package com.voltx.evgenee;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class EvGeneeSpringBootApplication {

    public static void main(String[] args) {
        SpringApplication.run(EvGeneeSpringBootApplication.class, args);
    }
}
