package com.moodstation.springboot.repository;

import com.moodstation.springboot.dto.UserPostSearchDto;
import com.moodstation.springboot.entity.UserPost;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface UserPostSearchRepository {
    Page<UserPost> userPostSearchPage(String accessToken, UserPostSearchDto userPostSearchDto, Pageable pageable);
}
