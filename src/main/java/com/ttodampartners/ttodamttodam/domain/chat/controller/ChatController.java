package com.ttodampartners.ttodamttodam.domain.chat.controller;

import com.ttodampartners.ttodamttodam.domain.chat.dto.request.ChatMessageRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RequiredArgsConstructor
@RestController
public class ChatController {
    private final SimpMessagingTemplate simpMessagingTemplate;

    // "/chattings/{userChatroomId}/messages"로 전송되는 메시지 처리 핸들러
    @MessageMapping("/{userChatroomId}/messages")
    public void chat(@DestinationVariable Long userChatroomId, ChatMessageRequest request) {
        // 메시지를 "/chatrooms/{userChatroomId}" 엔드포인트로 전송
        simpMessagingTemplate.convertAndSend("/chatrooms" + userChatroomId, request.getContent());

        log.info("Message [{}] send by member: {}(id: {}) to chatting room id: {}", request.getContent(), request.getNickname(), request.getSenderId(), userChatroomId);
    }
}
