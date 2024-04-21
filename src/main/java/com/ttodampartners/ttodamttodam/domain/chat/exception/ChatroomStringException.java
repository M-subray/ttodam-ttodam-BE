package com.ttodampartners.ttodamttodam.domain.chat.exception;

import com.ttodampartners.ttodamttodam.global.error.ErrorCode;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

/*
    ChatroomException과 다른 점은 이 exception은 단순히 String 메시지 반환
*/

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ChatroomStringException extends RuntimeException{
    private ErrorCode errorCode;
    private String errorMessage;

    public ChatroomStringException(ErrorCode errorCode) {
        this.errorCode = errorCode;
        this.errorMessage = errorCode.getDescription();
    }
}
