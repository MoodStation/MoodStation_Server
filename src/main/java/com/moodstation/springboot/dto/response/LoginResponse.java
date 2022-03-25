package com.moodstation.springboot.dto.response;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
public class LoginResponse {

    private Long userId;
    private String accessToken;
    private String refreshToken;
}
