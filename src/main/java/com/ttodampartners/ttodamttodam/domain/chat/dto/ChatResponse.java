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
    private String resultMsg;
    private T resultData;

    public ChatResponse(final HttpStatus statusCode, final String resultMsg) {
        this.statusCode = statusCode;
        this.resultMsg = resultMsg;
        this.resultData = null;
    }

    public static<T> ChatResponse<T> res(final HttpStatus statusCode, final String resultMsg) {
        return res(statusCode, resultMsg, null);
    }

    public static<T> ChatResponse<T> res(final HttpStatus statusCode, final String resultMsg, final T t) {
        return ChatResponse.<T>builder()
                .resultData(t)
                .statusCode(statusCode)
                .resultMsg(resultMsg)
                .build();
    }
}
