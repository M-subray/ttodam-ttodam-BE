package com.ttodampartners.ttodamttodam.domain.chat.service;

import com.ttodampartners.ttodamttodam.domain.chat.dto.request.ChatMessageRequest;
import com.ttodampartners.ttodamttodam.domain.chat.entity.ChatMessageEntity;
import com.ttodampartners.ttodamttodam.domain.chat.entity.ChatroomEntity;
import com.ttodampartners.ttodamttodam.domain.chat.repository.ChatMessageRepository;
import com.ttodampartners.ttodamttodam.domain.chat.repository.ChatroomRepository;
import com.ttodampartners.ttodamttodam.domain.user.entity.UserEntity;
import com.ttodampartners.ttodamttodam.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class ChatService {
    private final UserRepository userRepository;
    private final ChatroomRepository chatroomRepository;
    private final ChatMessageRepository chatMessageRepository;

    @Transactional // 채팅 메시지 저장
    public void saveChatMessage(Long chatroomId, ChatMessageRequest request) {
        UserEntity user = userRepository.findById(request.getSenderId()).orElseThrow(IllegalArgumentException::new);
        ChatroomEntity chatroom = chatroomRepository.findByChatroomId(chatroomId).orElseThrow(IllegalArgumentException::new);

        // CHAT_MESSAGE 테이블에 insert
        Long chatMessageId = chatMessageRepository.save(
                ChatMessageEntity.builder().userEntity(user).chatroomEntity(chatroom).content(request.getContent()).nickname(request.getNickname()).build()
        ).getChatMessageId();
        // CHATROOM 테이블의 lastMessageId 업데이트
        chatroom.updateLastMessage(chatMessageId);
    }

    // 채팅 메시지 불러오기
}
