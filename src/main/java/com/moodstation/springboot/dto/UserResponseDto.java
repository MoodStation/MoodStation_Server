package com.moodstation.springboot.dto;

import com.moodstation.springboot.entity.User;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class UserResponseDto {

    private int status;
    private String message;
    private String email;
    private String nickname;

    public UserResponseDto(HttpStatus statusCode, User entity) {
        this.status = statusCode.value();
        this.message = "회원가입 성공";
        this.email = entity.getEmail();
        this.nickname = entity.getNickname();
    }
}
