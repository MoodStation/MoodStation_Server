package com.moodstation.springboot.repository;

import com.moodstation.springboot.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    // 이메일 중복 방지
    Optional<User> findByEmail(String email);

    // 닉네임 중복 방지
    Optional<User> findByNickname(String nickname);
}
