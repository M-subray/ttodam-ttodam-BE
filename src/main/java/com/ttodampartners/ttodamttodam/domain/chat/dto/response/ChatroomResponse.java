package com.ttodampartners.ttodamttodam.domain.chat.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

/*
    채탕방 생성 성공 후 response body
*/

@Getter
@Builder
@AllArgsConstructor
@RequiredArgsConstructor
public class ChatroomResponse {
    private Long chatroomId;
    private Long hostId;
    private int userCount;
    private String chatName;
    private LocalDateTime createAt;
    private List<ChatroomProfileResponse> profiles;
}
