package com.moodstation.springboot.repository;

import com.moodstation.springboot.entity.Keyword;
import com.moodstation.springboot.entity.User;
import com.moodstation.springboot.entity.UserPost;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface KeywordRepository extends JpaRepository<Keyword, Long> {
    List<Keyword> findByUserPost(UserPost userPost);

    List<Keyword> findByUser(User user);
}
