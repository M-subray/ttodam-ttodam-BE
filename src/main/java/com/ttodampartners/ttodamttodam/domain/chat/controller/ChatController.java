package com.ttodampartners.ttodamttodam.domain.chat.controller;

import com.ttodampartners.ttodamttodam.domain.chat.dto.request.ChatMessageRequest;
import com.ttodampartners.ttodamttodam.domain.chat.exception.ChatroomStringException;
import com.ttodampartners.ttodamttodam.domain.chat.repository.ChatroomRepository;
import com.ttodampartners.ttodamttodam.domain.chat.service.ChatService;
import com.ttodampartners.ttodamttodam.global.error.ErrorCode;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RequiredArgsConstructor
@RestController
public class ChatController {
    private final SimpMessagingTemplate simpMessagingTemplate;
    private final ChatroomRepository chatroomRepository;
    private final ChatService chatService;

    /*
        "/chattings/{chatroomId}/messages"로 전송되는 메시지 처리 핸들러
    */

    @MessageMapping("/{chatroomId}/messages")
    public void chat(@DestinationVariable Long chatroomId, @Valid ChatMessageRequest request, @AuthenticationPrincipal Long senderId) {
        // 채팅방 존재 여부 확인
        chatroomRepository.findByChatroomId(chatroomId).orElseThrow(() -> new ChatroomStringException(ErrorCode.CHATROOM_NOT_FOUND));

        // 채팅 메시지 DB 저장
        chatService.saveChatMessage(chatroomId, request, senderId);
        // 받은 메시지를 "/chatroom/{userChatroomId}" 엔드포인트로 전송
        simpMessagingTemplate.convertAndSend("/chatroom/" + chatroomId, request.getContent());

        log.info("Message [{}] send by member: {}(id: {}) to chatting room id: {}", request.getContent(), request.getNickname(), senderId, chatroomId);
    }
}
