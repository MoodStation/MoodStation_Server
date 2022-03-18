package com.moodstation.springboot.dto;

import com.moodstation.springboot.entity.User;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class UserCreateResponseDto {

    private HttpStatus statusCode;
    private String email;
    private String nickname;

    public UserCreateResponseDto(HttpStatus statusCode, UserCreateRequestDto entity) {
        this.statusCode = statusCode;
        this.email = entity.getEmail();
        this.nickname = entity.getNickname();
    }
}
