package com.moodstation.springboot.dto.request;

import com.moodstation.springboot.entity.User;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@NoArgsConstructor
public class LoginDto {

    private String email;
    private String password;

    @Builder
    public LoginDto(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public User toEntity() {
        return User.builder()
                .email(email)
                .password(password)
                .build();
    }
}
