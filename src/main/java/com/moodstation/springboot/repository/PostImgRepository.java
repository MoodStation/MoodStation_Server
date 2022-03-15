package com.moodstation.springboot.repository;

import com.moodstation.springboot.entity.PostImg;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostImgRepository extends JpaRepository<PostImg,Long> {
}
