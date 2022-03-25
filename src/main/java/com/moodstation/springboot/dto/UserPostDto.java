package com.moodstation.springboot.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.moodstation.springboot.entity.PostImg;
import com.moodstation.springboot.entity.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.util.List;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserPostDto {
    private Long id;

    private User user;

    @JsonFormat(shape=JsonFormat.Shape.STRING,pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate regDate;

    private String color;

    private PostImg postImg;

    private String content;

    private List<String> keywords;

}
