package com.sparta.post_crud.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;


@NoArgsConstructor
@Getter
public class ReviewRequestDto {
    private Long post;

    private String username;

    private String content;

    public ReviewRequestDto(Long post, String username, String content){
        this.post= post;
        this.username = username;
        this.content = content;
    }
}
