package com.moodstation.springboot.service;

import com.moodstation.springboot.dto.UserCreateRequestDto;
import com.moodstation.springboot.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@RequiredArgsConstructor
@Service
public class UserService {

    private final UserRepository userRepository;

    // 회원가입
    @Transactional
    public Long save(UserCreateRequestDto requestDto) {
        return userRepository.save(requestDto.toEntity()).getId();
    }
}
