package com.sparta.post_crud.entity;

import com.sparta.post_crud.dto.ReviewRequestDto;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@NoArgsConstructor
@Entity
public class Review extends Timestamped{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column(nullable = false)
    private Long post;
    @Column(nullable = false)
    private String username;
    @Column(nullable = false)
    private String content;

    public Review(Long post, String username, String content){
        this.post= post;
        this.username = username;
        this.content = content;
    }

    public Review(ReviewRequestDto requestDto, String username){
        this.post = requestDto.getPost();
        this.username = username;
        this.content = requestDto.getContent();
    }

    public void update(ReviewRequestDto requestDto, String username){
        this.post = requestDto.getPost();
        this.username = username;
        this.content = requestDto.getContent();
    }
}
