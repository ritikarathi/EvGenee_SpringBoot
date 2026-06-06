package com.voltx.evgenee;

import org.apache.naming.factory.BeanFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class EvGeneeSpringBootApplication {

    public static void main(String[] args) {
        ConfigurableApplicationContext container = SpringApplication.run
                (EvGeneeSpringBootApplication.class, args);

    }

}
