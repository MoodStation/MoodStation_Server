package com.moodstation.springboot.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PostImgDto {
    private Long id;
    private String imgName;
    private String filePath;
    private String fileFullPath;
}
