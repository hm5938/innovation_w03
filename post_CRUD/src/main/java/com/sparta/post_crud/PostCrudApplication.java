package com.sparta.post_crud;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@ServletComponentScan("lecturer")
@SpringBootApplication
public class PostCrudApplication {

    public static void main(String[] args) {
        SpringApplication.run(PostCrudApplication.class, args);
    }
}
