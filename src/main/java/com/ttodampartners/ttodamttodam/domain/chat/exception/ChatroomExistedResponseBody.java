package com.ttodampartners.ttodamttodam.domain.chat.exception;

import com.ttodampartners.ttodamttodam.global.error.ErrorCode;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@RequiredArgsConstructor
public class ChatroomExistedResponseBody {
    private Long chatroomId;
}
