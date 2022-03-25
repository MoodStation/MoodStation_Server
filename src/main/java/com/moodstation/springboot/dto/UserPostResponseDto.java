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
public class UserPostResponseDto {
    private Long postId;
    private LocalDate regDate;
    private String color;
    private PostImg postImg;
    private List<String> keywords;


    @Builder
    public UserPostResponseDto(Long postId, LocalDate regDate, String color, PostImg postImg, List<String> keywords) {
        this.postId = postId;
        this.regDate = regDate;
        this.color = color;
        this.postImg = postImg;
        this.keywords = keywords;
    }
}
