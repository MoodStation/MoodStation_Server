package com.moodstation.springboot.repository;

import com.moodstation.springboot.entity.Keyword;
import org.springframework.data.jpa.repository.JpaRepository;

public interface KeywordRepository extends JpaRepository<Keyword, Long> {
}
