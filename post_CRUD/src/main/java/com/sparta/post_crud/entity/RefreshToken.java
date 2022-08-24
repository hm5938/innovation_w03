package com.sparta.post_crud.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Setter
@Getter // get 함수를 일괄적으로 만들어줍니다.
@NoArgsConstructor // 기본 생성자를 만들어줍니다.
@Entity // DB 테이블 역할을 합니다.
public class RefreshToken {

    @Id
    private Long id;
    @Column(nullable = false)
    private String token;

    public RefreshToken(Long id, String token){
        this.id= id;
        this.token = token;
    }

}
