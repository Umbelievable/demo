package com.example.demo.payload.request;

import com.example.demo.model.user.ESex;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.*;
import java.time.LocalDate;
import java.util.Set;

@Getter
@Setter
public class SignupRequest {

    // 회원가입 할 때 필요한 정보들

    @NotBlank
    @Size(min = 5, max = 20)
    private String id;

    // 유저 아이디
    @NotBlank
    @Size(min = 3, max = 20)
    private String username;

    @NotBlank
    @Email
    @Size(max = 50)
    private String email;

    private Set<String> roles;

    @NotBlank
    @Size(min = 6, max = 40)
    private String password;


    // 핸드폰 번호
    private String phone;

    // 성별
    private ESex sex;

    // 생일
    private LocalDate birthDate;

}