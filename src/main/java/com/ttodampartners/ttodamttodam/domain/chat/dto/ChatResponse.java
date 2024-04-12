package com.ttodampartners.ttodamttodam.domain.chat.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.springframework.http.HttpStatus;

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

    // 전달할 데이터는 따로 없는 경우
    public static<T> ChatResponse<T> res(final HttpStatus statusCode, final String message) {
        return res(statusCode, message, null);
    }

    public static<T> ChatResponse<T> res(final HttpStatus statusCode, final String message, final T t) {
        return ChatResponse.<T>builder()
                .data(t)
                .statusCode(statusCode)
                .message(message)
                .build();
    }
}
