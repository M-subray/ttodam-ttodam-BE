package com.ttodampartners.ttodamttodam.domain.chat.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.springframework.http.HttpStatus;

/*
 * 채팅 관련 Exception API 공통 응답 템플릿
*/

@Data
@AllArgsConstructor
@Builder
public class ChatExceptionResponse<T> {
    private HttpStatus code;
    private String message;
    private T data;

    public ChatExceptionResponse(final HttpStatus code, final String message) {
        this.code = code;
        this.message = message;
        this.data = null;
    }

    // 전달할 데이터는 따로 없이 상태코드, 상태코드 메시지만 전달
    public static<T> ChatExceptionResponse<T> res(final HttpStatus code, final String message) {
        return res(code, message, null);
    }

    // 상태코드, 상태코드 메시지, 데이터
    public static<T> ChatExceptionResponse<T> res(final HttpStatus code, final String message, final T t) {
        return ChatExceptionResponse.<T>builder()
                .data(t)
                .code(code)
                .message(message)
                .build();
    }
}
