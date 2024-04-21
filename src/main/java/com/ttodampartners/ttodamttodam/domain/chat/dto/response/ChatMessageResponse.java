package com.ttodampartners.ttodamttodam.domain.chat.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;

@Getter
@Builder
@AllArgsConstructor
@RequiredArgsConstructor
public class ChatMessageResponse {
    private Long messageId;
    private Long chatroomId;
    private Long senderId;
    private String nickname;
    private String content;
    private LocalDateTime messageCreateAt;
}
