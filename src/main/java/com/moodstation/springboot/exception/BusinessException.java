package com.moodstation.springboot.exception;

import com.moodstation.springboot.enums.ErrorCode;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class BusinessException extends RuntimeException {

    ErrorCode errorCode;
    HttpStatus status;

    public BusinessException(String message, ErrorCode errorCode, HttpStatus status) {
        super(message);
        this.errorCode = errorCode;
        this.status = status;
    }
}
