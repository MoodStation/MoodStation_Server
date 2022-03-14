package com.moodstation.springboot.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Keyword {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String content;

    private String isShare;

    @ManyToOne(fetch = FetchType.LAZY)
    private UserPost userPost;

    @Builder
    public Keyword(String content, String isShare, UserPost userPost) {
        this.content = content;
        this.isShare = isShare;
        this.userPost = userPost;
    }
}
