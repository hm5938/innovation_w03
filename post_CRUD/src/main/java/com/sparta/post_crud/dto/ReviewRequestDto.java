package com.sparta.post_crud.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;


@NoArgsConstructor
@Getter
public class ReviewRequestDto {
    private Long post;
    private String content;

    public ReviewRequestDto(Long post, String content){
        this.post= post;
        this.content = content;
    }
}
