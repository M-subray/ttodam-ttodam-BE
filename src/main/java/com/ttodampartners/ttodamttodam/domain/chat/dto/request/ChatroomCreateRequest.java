package com.ttodampartners.ttodamttodam.domain.chat.dto.request;

import lombok.Getter;

/*
 * 채팅방 생성 request body
*/

@Getter
public class ChatroomCreateRequest {
    private Long userId;
    private Long postId;
}
