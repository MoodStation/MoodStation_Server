package com.moodstation.springboot.dto;

import com.moodstation.springboot.enums.StatusEnum;
import lombok.Data;

@Data
public class Message {
    private StatusEnum status;
    private String message;
    private Object data;

    public Message() {
        this.status = StatusEnum.BAD_REQUEST;
        this.data = null;
        this.message = null;
    }
}