package com.moodstation.springboot.service;

import com.moodstation.springboot.dto.UserCreateRequestDto;
import com.moodstation.springboot.dto.UserCreateResponseDto;
import com.moodstation.springboot.entity.User;
import com.moodstation.springboot.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@RequiredArgsConstructor
@Service
public class UserService {

    private final UserRepository userRepository;

    // 회원가입
    public String save(UserCreateRequestDto requestDto) {
        userRepository.save(User.builder()
                .email(requestDto.getEmail())
                .nickname(requestDto.getNickname())
                .password(requestDto.getPassword())
                .build());

        return "Success";
    }
}
