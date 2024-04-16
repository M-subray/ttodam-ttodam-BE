package com.ttodampartners.ttodamttodam.domain.chat.dto.response;

/*
 * 채팅방 생성 성공후 response body로 전달할 채팅방 참여 유저들 정보
 * ChatroomResponse와 ChatroomListResponse에 사용하기 위함
*/

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ChatroomResponseProfile {
    private Long userId;
    private String nickname;
    private String profileImage;
}
