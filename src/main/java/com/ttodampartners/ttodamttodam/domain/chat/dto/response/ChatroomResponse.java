package com.ttodampartners.ttodamttodam.domain.chat.dto.response;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ChatroomResponse {
    private Long userChatroomId;
    private Long senderId;
}
