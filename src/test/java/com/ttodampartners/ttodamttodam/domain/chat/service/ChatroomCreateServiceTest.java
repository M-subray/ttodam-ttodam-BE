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
public class ChatroomCreateServiceTest {

    @Autowired
    private ChatroomCreateService chatroomCreateService;

    @Test
    @DisplayName("개인 채팅방 생성 완료")
    void CREATE_CHATROOM_TEST() {
        ChatroomCreateRequest chatroomCreateRequest = ChatroomCreateRequest.builder().postId(2L).build();

        ChatroomResponse chatroomResponse = chatroomCreateService.createChatroom(chatroomCreateRequest, 3L);

        assertNotNull(chatroomResponse);
        assertNotNull(chatroomResponse.getChatroomId());
        assertNotNull(chatroomResponse.getProfiles());
    }
}
