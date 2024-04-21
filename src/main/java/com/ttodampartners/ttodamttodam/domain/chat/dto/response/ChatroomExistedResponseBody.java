package com.ttodampartners.ttodamttodam.domain.chat.dto.response;

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
