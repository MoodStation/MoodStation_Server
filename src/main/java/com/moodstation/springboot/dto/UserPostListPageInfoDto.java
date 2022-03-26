package com.moodstation.springboot.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserPostListPageInfoDto {
    private int pageSize;
    private long totalElements;

    @Builder
    public UserPostListPageInfoDto(int pageSize, long totalElements) {
        this.pageSize = pageSize;
        this.totalElements = totalElements;
    }
}
