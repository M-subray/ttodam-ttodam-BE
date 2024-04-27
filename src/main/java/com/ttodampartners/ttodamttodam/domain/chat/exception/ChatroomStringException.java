package com.ttodampartners.ttodamttodam.domain.chat.exception;

import com.ttodampartners.ttodamttodam.global.error.ErrorCode;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ChatroomStringException extends RuntimeException{
    private ErrorCode errorCode;
    private String errorMessage;

    public ChatroomStringException(String message) {
        this.errorCode = null;
        this.errorMessage = message;
    }

    public ChatroomStringException(ErrorCode errorCode) {
        this.errorCode = errorCode;
        this.errorMessage = errorCode.getDescription();
    }
}
