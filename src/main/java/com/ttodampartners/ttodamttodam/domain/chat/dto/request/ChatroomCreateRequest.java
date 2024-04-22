package com.ttodampartners.ttodamttodam.domain.chat.dto.request;

import lombok.Getter;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

/*
 * 채팅방 생성 request body
*/
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class ChatroomCreateRequest {
    private Long postId;
}
