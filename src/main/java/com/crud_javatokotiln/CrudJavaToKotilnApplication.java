package com.crud_javatokotiln;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class CrudJavaToKotilnApplication {

    public static void main(String[] args) {
        SpringApplication.run(CrudJavaToKotilnApplication.class, args);
    }

}
