package com.moodstation.springboot.web;

import com.moodstation.springboot.dto.UserCreateRequestDto;
import com.moodstation.springboot.dto.UserCreateResponseDto;
import com.moodstation.springboot.entity.User;
import com.moodstation.springboot.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class UserApiController {

    private final UserService userService;

    @PostMapping("/api/v1/signup")
    public UserCreateResponseDto signup(@RequestBody UserCreateRequestDto requestDto) {
        if (userService.save(requestDto).equals("Success")) {
            return new UserCreateResponseDto(HttpStatus.CREATED, requestDto);
        }

        return new UserCreateResponseDto(HttpStatus.BAD_REQUEST, requestDto);
    }

}
