package com.moodstation.springboot.dto.request;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CheckEmailDto {
    private String email;
}
