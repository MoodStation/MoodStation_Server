package com.moodstation.springboot.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
public class PostImgDto {
    private Long id;
    private String imgName;
    private String filePath;
    private String fileFullPath;

    @Builder
    public PostImgDto(Long id, String imgName, String filePath, String fileFullPath) {
        this.id = id;
        this.imgName = imgName;
        this.filePath = filePath;
        this.fileFullPath = fileFullPath;
    }
}
