package com.sparta.post_crud.service;

import com.sparta.post_crud.dto.PostRequestDto;
import com.sparta.post_crud.entity.Post;
import com.sparta.post_crud.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@RequiredArgsConstructor
@Service
public class PostService {

    private final PostRepository postRepository;

    public Long update(Long id, PostRequestDto postRequestDto){
        Post post1 = postRepository.findById(id).orElseThrow(()-> new IllegalArgumentException("해당 아이디가 존재하지 않습니다."));
        post1.update(postRequestDto);
        return post1.getId();
    }

    public Post read(Long id){
        Post post1= postRepository.findById(id).orElseThrow(()-> new IllegalArgumentException());
        return post1;
    }

    public boolean checkPassword(Long id, String password){
        Post post1 = postRepository.findById(id).orElseThrow(()-> new IllegalArgumentException());
        return post1.getPassword().equals(password);
    }

    public Long delete(Long id){
        postRepository.deleteById(id);
        return id;
    }

    public Post createPost(PostRequestDto postRequestDto){
        Post post = new Post(postRequestDto);
        return postRepository.save(post);
    }


    public List<Post> getList(){
        List<Post> posts= postRepository.findAll(Sort.by("createdAt"));
        return posts;
    }

}
