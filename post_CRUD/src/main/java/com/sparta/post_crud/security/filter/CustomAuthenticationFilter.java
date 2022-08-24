package com.sparta.post_crud.security.filter;

import com.sparta.post_crud.security.jwt.TokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@RequiredArgsConstructor
public class CustomAuthenticationFilter extends OncePerRequestFilter {

    private final TokenProvider jwtTokenProvider;
    // request header 에서 토큰을 가져오거나

    // Request로 들어오는 Jwt Token의 유효성을 검증하는 filter를 filterChain에 등록합니다.
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String accessToken = request.getHeader("Access-Token");//jwtTokenProvider.resolveToken((HttpServletRequest) request);
        String refreshToken = request.getHeader("Refresh-Token");
        if (accessToken != null && jwtTokenProvider.validateToken(accessToken)) {   // token 검증
            Authentication auth = jwtTokenProvider.getAuthentication(accessToken);    // 인증 객체 생성
            SecurityContextHolder.getContext().setAuthentication(auth); // SecurityContextHolder에 인증 객체 저장
        }else if(refreshToken!= null && jwtTokenProvider.validateToken(refreshToken)){
            accessToken = jwtTokenProvider.generateAccessToken(refreshToken); //token 재발금
            Authentication auth = jwtTokenProvider.getAuthentication(accessToken);    // 인증 객체 생성
            SecurityContextHolder.getContext().setAuthentication(auth); // SecurityContextHolder에 인증 객체 저장
            response.addHeader("Access-Token",accessToken);
        }
        filterChain.doFilter(request, response);
    }


}