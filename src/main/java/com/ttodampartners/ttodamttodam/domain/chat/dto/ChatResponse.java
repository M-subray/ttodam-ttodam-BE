package com.ttodampartners.ttodamttodam.domain.chat.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.springframework.http.HttpStatus;

/*
* 채팅 관련 API 공통 응답 템플릿 (현재는 잠시 사용 보류)
* */

@Data
@AllArgsConstructor
@Builder
public class ChatResponse<T> {
    private HttpStatus statusCode;
    private String message;
    private T data;

    public ChatResponse(final HttpStatus statusCode, final String message) {
        this.statusCode = statusCode;
        this.message = message;
        this.data = null;
    }

    // 전달할 데이터는 따로 없이 상태코드, 상태코드 메시지만 전달
    public static<T> ChatResponse<T> res(final HttpStatus statusCode, final String message) {
        return res(statusCode, message, null);
    }

    // 상태코드, 상태코드 메시지, 데이터
    public static<T> ChatResponse<T> res(final HttpStatus statusCode, final String message, final T t) {
        return ChatResponse.<T>builder()
                .data(t)
                .statusCode(statusCode)
                .message(message)
                .build();
    }
}
