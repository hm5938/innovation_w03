package com.sparta.post_crud;

import com.sparta.post_crud.dto.PostRequestDto;
import com.sparta.post_crud.entity.Post;
import com.sparta.post_crud.repository.PostRepository;
import com.sparta.post_crud.service.PostService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import java.util.List;
@EnableJpaAuditing
@SpringBootApplication
public class PostCrudApplication {

    public static void main(String[] args) {
        SpringApplication.run(PostCrudApplication.class, args);
    }
    @Bean
    public CommandLineRunner demo(PostRepository postRepository, PostService postService) {
        return (args) -> {
        };
    }
}
