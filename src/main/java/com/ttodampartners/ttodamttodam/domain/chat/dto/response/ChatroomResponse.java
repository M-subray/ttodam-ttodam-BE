package com.ttodampartners.ttodamttodam.domain.chat.dto.response;

import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@AllArgsConstructor
@RequiredArgsConstructor
@Builder
public class ChatroomResponse {
    private Long userChatroomId;
    private Long hostId;
    private int userCount;
    private String chatName;
    private LocalDateTime createdAt;
    private List<ChatroomResponseProfile> profiles;

}
