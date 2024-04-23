package com.ttodampartners.ttodamttodam.domain.chat.service;

import com.ttodampartners.ttodamttodam.domain.chat.dto.request.ChatroomCreateRequest;
import com.ttodampartners.ttodamttodam.domain.chat.dto.response.ChatroomResponse;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertNotNull;


@SpringBootTest
@DisplayName("개인 채팅방 생성 확인")
public class ChatroomServiceTest {

    /*
        - 혹시 테스트 실패한다면 아마 PostEntity에서 상품 이미지를 List로 받아오기 때문
        - 현재는 ChatroomException까지 잘 동작
    */

    @Autowired
    private ChatroomService chatroomService;

    @Test
    @DisplayName("개인 채팅방 생성 완료")
    void CREATE_CHATROOM_TEST() {
        ChatroomCreateRequest chatroomCreateRequest = ChatroomCreateRequest.builder().postId(2L).build();

        ChatroomResponse chatroomResponse = chatroomService.createChatroom(chatroomCreateRequest, 3L);

        assertNotNull(chatroomResponse);
        assertNotNull(chatroomResponse.getChatroomId());
        assertNotNull(chatroomResponse.getProfiles());
    }
}
