package com.sparta.post_crud.controller;

import com.sparta.post_crud.dto.LoginRequestDto;
import com.sparta.post_crud.dto.ResponseDto;
import com.sparta.post_crud.dto.SignupRequestDto;
import com.sparta.post_crud.service.UserService;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RestController
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }


    // 회원 가입 요청 처리
    @PostMapping("/api/users/signup")
    public ResponseDto<?> registerUser(@RequestBody SignupRequestDto requestDto) {
        return userService.registerUser(requestDto);
    }

    @PostMapping("/api/auth/users/logout")
    public ResponseDto<?> logout(HttpServletRequest request){
        return userService.logout(request);
    }

    @PostMapping("/api/users/login")
    public ResponseDto<?> login(@RequestBody LoginRequestDto loginRequestDto, HttpServletResponse response){
        return userService.login(loginRequestDto , response);
    }





}