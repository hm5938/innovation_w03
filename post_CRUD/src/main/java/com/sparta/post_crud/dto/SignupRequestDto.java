package com.sparta.post_crud.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.intellij.lang.annotations.Pattern;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class SignupRequestDto {

//    @NotBlank
//    @Size(min = 4, max =12)
//    @Pattern(regexp = "[a-zA-Z\\d]*${3,12}")
    private String username;

//    @NotBlank
//    @Size(min = 4, max =12)
//    @Pattern(regexp = "[a-z\\d]*${3,32}")
    private String password;
    private String passwordAgain;
}