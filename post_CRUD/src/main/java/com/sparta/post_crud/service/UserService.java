package com.sparta.post_crud.service;

import com.sparta.post_crud.dto.LoginRequestDto;
import com.sparta.post_crud.dto.ResponseDto;
import com.sparta.post_crud.dto.SignupRequestDto;
import com.sparta.post_crud.dto.TokenDto;
import com.sparta.post_crud.entity.User;
import com.sparta.post_crud.repository.UserRepository;

import com.sparta.post_crud.security.jwt.TokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Optional;
import java.util.regex.Pattern;

@Service
@RequiredArgsConstructor
public class UserService {
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final TokenProvider tokenProvider;

    public ResponseDto<User> registerUser(SignupRequestDto requestDto) {
        // 회원 ID 중복 확인
        String username = requestDto.getUsername();
        Optional<User> found = userRepository.findByUsername(username);
        if (found.isPresent()) {
            throw new IllegalArgumentException("중복된 사용자 ID 가 존재합니다.");
        }
        String password = requestDto.getPassword();
        String passwordAgain = requestDto.getPasswordAgain();
        // 비밀번호 일치 확인
        if (!passwordAgain.equals(password)) {
            throw new IllegalArgumentException("비밀번호다 다릅니다.");
        }

                /*- 닉네임은 `최소 4자 이상, 12자 이하 알파벳 대소문자(a~z, A~Z), 숫자(0~9)`로 구성하기
              - 비밀번호는 `최소 4자 이상이며, 32자 이하 알파벳 소문자(a~z), 숫자(0~9)` 로 구성하기
              - 비밀번호 확인은 비밀번호와 정확하게 일치하기*/
        // ID, PW 구성 조건 확인
        String idPattern = "^[a-zA-Z0-9]{4,12}$";
        String pwPattern = "^[a-z0-9]{4,32}$";

        if(!Pattern.matches(idPattern, username) || !Pattern.matches(pwPattern, password)) {
            throw new IllegalArgumentException("닉네임은 `최소 4자 이상, 12자 이하 알파벳 대소문자(a~z, A~Z), 숫자(0~9)`로\n" +
                    "비밀번호는 `최소 4자 이상이며, 32자 이하 알파벳 소문자(a~z), 숫자(0~9)` 로 구성되어야 합니다");
        }

        // 비밀번호 암호화
        password = passwordEncoder.encode(password);

        User user = new User(username, password);
        userRepository.save(user);
        return ResponseDto.success(user);
    }


    public ResponseDto<?> login(LoginRequestDto requestDto, HttpServletResponse response){

        Optional<User> optionalUser =userRepository.findByUsername(requestDto.getUsername());
        if(optionalUser.isEmpty()){
            return ResponseDto.fail("NOT_FOUND", "사용자를 찾을 수 없습니다.");
        }

        String password =requestDto.getPassword();

        User user = optionalUser.get();
        if(!passwordEncoder.matches(password,user.getPassword())){
            return ResponseDto.fail("NOT_FOUND", "비밀번호가 다릅니다.");
        }

        TokenDto tokenDto = tokenProvider.generateTokenDto(user);

        response.addHeader("Access-Token",tokenDto.getAccessToken());
        response.addHeader("Refresh-Token",tokenDto.getRefreshToken());

       return ResponseDto.success(tokenDto);
    }

    public ResponseDto<?> logout(HttpServletRequest request){
        if(!tokenProvider.validateToken(request.getHeader("Refresh-Token"))){
            return ResponseDto.fail("INVALID_TOKEN","Token이 유효하지 않습니다");
        }
        User user = tokenProvider.getUserFromAuthentication(request.getHeader("Refresh-Token"));
        if(user ==null){
            return ResponseDto.fail("USER_NOT_FOUNT","사용자를 찾을 수 없습니다");
        }

        return tokenProvider.deleteRefreshToken(user);
    }



}