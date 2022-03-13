package com.moodstation.springboot.dto;

import com.moodstation.springboot.entity.User;
import lombok.Getter;

@Getter
public class UserCreateResponseDto {

    private Long id;
    private String email;
    private String nickname;

    public UserCreateResponseDto(User entity) {
        this.id = entity.getId();
        this.email = entity.getEmail();
        this.nickname = entity.getNickname();
    }
}
