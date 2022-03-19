package com.moodstation.springboot.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Getter
@NoArgsConstructor
public class UserPost {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    //@ManyToOne(fetch = FetchType.LAZY,cascade = CascadeType.REMOVE)
    @ManyToOne
    private User user;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate regDate;

    private String color;

    private String content;

    @OneToOne(cascade = CascadeType.PERSIST,orphanRemoval = true)
    private PostImg postImg;

    @Builder
    public UserPost(Long id, User user, LocalDate regDate,String content, String color, PostImg postImg) {
        this.id = id;
        this.user = user;
        this.regDate = regDate;
        this.color = color;
        this.postImg = postImg;
        this.content = content;
    }

    public void changeUserPost(LocalDate regDate, String color, String content, PostImg postImg) {
        this.regDate = regDate;
        this.color = color;
        this.content = content;
        this.postImg = postImg;
    }


}
