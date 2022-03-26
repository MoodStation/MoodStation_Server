package com.moodstation.springboot.repository;

import com.moodstation.springboot.entity.User;
import com.moodstation.springboot.entity.UserPost;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

import java.util.List;

public interface UserPostRepository extends JpaRepository<UserPost,Long>, QuerydslPredicateExecutor<UserPost>, UserPostSearchRepository {
    List<UserPost> findByUser(User user);
}
