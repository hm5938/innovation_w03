package com.sparta.post_crud.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class PostRequestDto {

    private String title;
    private String name;
    private String password;
    private String content;

    public PostRequestDto(String title, String name, String password, String content){
        this.title= title;
        this.name = name;
        this.password = password;
        this.content = content;
    }
}
