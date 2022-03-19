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
    @ManyToOne(cascade = CascadeType.REMOVE)
    private User user;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate regDate;

    private String color;

    @OneToOne(cascade = CascadeType.ALL)
    private PostImg postImg;


    @Builder
    public UserPost(Long id, User user, LocalDate regDate, String color, PostImg postImg) {
        this.id = id;
        this.user = user;
        this.regDate = regDate;
        this.color = color;
        this.postImg = postImg;
    }
}
