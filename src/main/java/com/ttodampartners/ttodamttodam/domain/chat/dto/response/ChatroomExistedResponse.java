package com.ttodampartners.ttodamttodam.domain.chat.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

/*
    채팅방 이미 존재할 경우 Exception 반환용
*/


@Getter
@Builder
@AllArgsConstructor
@RequiredArgsConstructor
public class ChatroomExistedResponse {
    private Long chatroomId;
}
