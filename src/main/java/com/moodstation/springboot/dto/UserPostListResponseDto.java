package com.moodstation.springboot.dto;

import com.moodstation.springboot.entity.PostImg;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class UserPostListResponseDto {
    private Long postId;
    private LocalDate regDate;
    private String color;
    private String content;
    private PostImg postImg;
    private List<String> keywords;

    @Builder
    public UserPostListResponseDto(Long postId, LocalDate regDate, String color, String content, PostImg postImg, List<String> keywords) {
        this.postId = postId;
        this.regDate = regDate;
        this.color = color;
        this.content = content;
        this.postImg = postImg;
        this.keywords = keywords;
    }
}
