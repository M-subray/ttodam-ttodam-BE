package com.ttodampartners.ttodamttodam.domain.chat.dto.request;

import lombok.Getter;
import lombok.NoArgsConstructor;

/*
* STOMP 메시지 보내기
* */

@Getter
@NoArgsConstructor
public class ChatMessageRequest {
    public enum MessageType{
        ENTER, TALK
    }

    private MessageType type;
    private Long senderId;
    private String nickname;
    private String content;
}
