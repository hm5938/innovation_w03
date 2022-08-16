package com.sparta.post_crud.controller;

import com.sparta.post_crud.dto.PostRequestDto;
import com.sparta.post_crud.dto.PostResponseDto;
import com.sparta.post_crud.entity.Post;
import com.sparta.post_crud.repository.PostRepository;
import com.sparta.post_crud.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
public class PostController {
    private final PostRepository postRepository;
    private final PostService postService;

    @GetMapping("/api/posts")
    public List<PostResponseDto> getPosts(){ return postService.getList();}


    @GetMapping("/api/posts/{id}")
    public Post getPost(@PathVariable Long id){ return postService.read(id); }

    @PostMapping("api/posts")
    public Post createPost(@RequestBody PostRequestDto postRequestDto){
        return postService.createPost(postRequestDto);
    }

    @DeleteMapping("api/posts/{id}")
    public Long deletePost(@PathVariable Long id){
        return postService.delete(id);
    }

    @PutMapping("api/posts/{id}")
    public Long updatePost(@PathVariable Long id, @RequestBody PostRequestDto postRequestDto){
        return postService.update(id,postRequestDto);
    }

    @PostMapping("api/posts/password/{id}")
    public boolean checkPassword(@PathVariable Long id, @RequestBody String password){
        return postService.checkPassword(id, password);
    }


}
