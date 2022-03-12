package com.moodstation.springboot.repository;

import com.moodstation.springboot.entity.UserPost;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserPostRepository extends JpaRepository<UserPost,Long> {
}
