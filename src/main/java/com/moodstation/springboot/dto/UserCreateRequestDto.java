package com.moodstation.springboot.dto;

import com.moodstation.springboot.entity.User;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Getter
@NoArgsConstructor
public class UserCreateRequestDto {
    BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

    private String email;
    private String nickname;
    private String password;

    @Builder
    public UserCreateRequestDto(String email, String nickname, String password) {
        this.email = email;
        this.nickname = nickname;
        this.password = encoder.encode(password);
    }

    public User toEntity() {
        return User.builder()
                .email(email)
                .nickname(nickname)
                .password(password)
                .build();
    }
}
