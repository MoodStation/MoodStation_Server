package com.moodstation.springboot.exception;

import com.moodstation.springboot.enums.ErrorCode;
import org.springframework.http.HttpStatus;

public class ConflictExecption extends BusinessException {

    public ConflictExecption(String message, ErrorCode errorCode, HttpStatus status) {
        super(message, errorCode, status);
    }
}
