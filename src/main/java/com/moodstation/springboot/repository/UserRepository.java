package com.moodstation.springboot.repository;

import com.moodstation.springboot.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {

    User findUserByEmail(String email);
    User findUserByNickname(String nickname);
    User findByAccessToken(String accessToken);
}
