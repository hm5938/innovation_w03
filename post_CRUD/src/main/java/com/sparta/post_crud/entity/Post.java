package com.sparta.post_crud.entity;

import com.sparta.post_crud.dto.PostRequestDto;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@NoArgsConstructor
@Entity
public class Post extends Timestamped{

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable = false)
    private String title;
    @Column(nullable = false)
    private String name;
    @Column(nullable = false)
    private String password;
    @Column(nullable = false)
    private String content;

    public Post(String title, String name, String password, String content){
        this.title = title;
        this.name = name;
        this.password = password;
        this.content = content;
    }

    public Post(PostRequestDto postRequestDto){
        this.title = postRequestDto.getTitle();
        this.name= postRequestDto.getName();
        this.password = postRequestDto.getPassword();
        this.content = postRequestDto.getContent();
    }

    public void update(PostRequestDto postRequestDto){
        this.title = postRequestDto.getTitle();
        this.name= postRequestDto.getName();
        this.password = postRequestDto.getPassword();
        this.content = postRequestDto.getContent();
    }



}
