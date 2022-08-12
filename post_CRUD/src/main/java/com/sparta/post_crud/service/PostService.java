package com.sparta.post_crud.service;

import com.sparta.post_crud.dto.PostRequestDto;
import com.sparta.post_crud.entity.Post;
import com.sparta.post_crud.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class PostService {

    private final PostRepository postRepository;

    public Long update(Long id, PostRequestDto postRequestDto){
        Post post1 = postRepository.findById(id).orElseThrow(()-> new IllegalArgumentException("해당 아이디가 존재하지 않습니다."));
        post1.update(postRequestDto);
        return post1.getId();
    }
}
