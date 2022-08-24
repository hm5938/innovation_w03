package com.sparta.post_crud.controller;


import com.sparta.post_crud.UserDetailsImpl;
import com.sparta.post_crud.dto.PasswordDto;
import com.sparta.post_crud.dto.PostRequestDto;
import com.sparta.post_crud.dto.ResponseDto;

import com.sparta.post_crud.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;


@RequiredArgsConstructor
@RestController
public class PostController {
    private final PostService postService;

    @GetMapping("/api/posts")
    public ResponseDto<?> getPosts(){ return postService.getAllPost();}


    @GetMapping("/api/posts/{id}")
    public ResponseDto<?> getPost(@PathVariable Long id){ return postService.getPost(id); }

    @PostMapping("api/posts")
    public ResponseDto<?> createPost(@RequestBody PostRequestDto postRequestDto, @AuthenticationPrincipal UserDetailsImpl userDetails){
        String username = userDetails.getUser().getUsername();
        return postService.createPost(postRequestDto ,username);
    }

    @DeleteMapping("api/posts/{id}")
    public ResponseDto<?> deletePost(@PathVariable Long id, @AuthenticationPrincipal UserDetailsImpl userDetails){
        String username = userDetails.getUser().getUsername();
        return postService.deletePost(id,username);
    }

    @PutMapping("api/posts/{id}")
    public ResponseDto<?> updatePost(@PathVariable Long id, @RequestBody PostRequestDto postRequestDto, @AuthenticationPrincipal UserDetailsImpl userDetails){
        String username = userDetails.getUser().getUsername();
        return postService.updatePost(id,postRequestDto,username);
    }

    @PostMapping("api/posts/password/{id}")
    public ResponseDto<?> checkPassword(@PathVariable Long id, @RequestBody PasswordDto password){
        return postService.validateAuthorByPassword(id,password);
    }


}
