package com.ttodampartners.ttodamttodam.domain.chat.exception;

import com.ttodampartners.ttodamttodam.domain.chat.dto.ChatExceptionResponse;
import com.ttodampartners.ttodamttodam.global.error.ErrorCode;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class StompException extends RuntimeException{
    private ErrorCode errorCode;
    private String errorMessage;
    private ChatExceptionResponse response;

    public StompException(ErrorCode errorCode, ChatExceptionResponse response) {
        this.errorCode = errorCode;
        this.errorMessage = errorCode.getDescription();
        this.response = response;
    }
}
