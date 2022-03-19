package com.moodstation.springboot.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor
public class PostImg {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String imgName;

    @Column(columnDefinition = "TEXT")
    private String filePath;

    private String fileFullPath;
}
