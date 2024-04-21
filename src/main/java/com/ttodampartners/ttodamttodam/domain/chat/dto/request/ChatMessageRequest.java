package com.ttodampartners.ttodamttodam.domain.chat.dto.request;

import lombok.Getter;
import lombok.NoArgsConstructor;

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
