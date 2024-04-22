package com.ttodampartners.ttodamttodam.domain.chat.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.springframework.http.HttpStatus;

/*
 * 채팅 관련 API 공통 응답 템플릿 (현재는 잠시 사용 보류)
*/

@Data
@AllArgsConstructor
@Builder
public class ChatCommonResponse<T> {
    private HttpStatus code;
    private String message;
    private T data;

    public ChatCommonResponse(final HttpStatus code, final String message) {
        this.code = code;
        this.message = message;
        this.data = null;
    }

    // 전달할 데이터는 따로 없이 상태코드, 상태코드 메시지만 전달
    public static<T> ChatCommonResponse<T> res(final HttpStatus code, final String message) {
        return res(code, message, null);
    }

    // 상태코드, 상태코드 메시지, 데이터
    public static<T> ChatCommonResponse<T> res(final HttpStatus code, final String message, final T t) {
        return ChatCommonResponse.<T>builder()
                .data(t)
                .code(code)
                .message(message)
                .build();
    }
}
