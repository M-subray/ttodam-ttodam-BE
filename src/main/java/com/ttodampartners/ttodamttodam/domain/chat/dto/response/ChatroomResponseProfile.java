package com.ttodampartners.ttodamttodam.domain.chat.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

/*
* 채팅방 생성 성공후 response body로 전달할 채팅방 참여 유저들 정보
* ChatroomResponse에 사용하기 위함
* */

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ChatroomResponseProfile {
    private Long userId;
    private String nickname;
    private String profileImage;
}
