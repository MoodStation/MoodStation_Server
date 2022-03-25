package com.moodstation.springboot.service;

import com.moodstation.springboot.entity.User;
import com.moodstation.springboot.enums.RoleType;
import com.moodstation.springboot.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public User createUser(User user) {
        LocalDateTime now = LocalDateTime.now();
        user.setCreatedAt(now);
        user.setUpdatedAt(now);
        user.setRole(RoleType.USER);

        return userRepository.save(user);
    }

    public Boolean checkEmail(String email) {
        return userRepository.findUserByEmail(email) == null;
    }

    public Boolean checkNickname(String nickname) {
        return userRepository.findUserByNickname(nickname) == null;
    }
}
