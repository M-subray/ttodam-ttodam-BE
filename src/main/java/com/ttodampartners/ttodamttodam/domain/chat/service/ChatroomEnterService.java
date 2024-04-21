package com.ttodampartners.ttodamttodam.domain.chat.service;

import com.ttodampartners.ttodamttodam.domain.chat.dto.response.ChatMessageResponse;
import com.ttodampartners.ttodamttodam.domain.chat.dto.response.ChatroomEnterResponse;
import com.ttodampartners.ttodamttodam.domain.chat.dto.response.ChatroomProfileResponse;
import com.ttodampartners.ttodamttodam.domain.chat.entity.ChatMessageEntity;
import com.ttodampartners.ttodamttodam.domain.chat.entity.ChatroomEntity;
import com.ttodampartners.ttodamttodam.domain.chat.entity.ChatroomMemberEntity;
import com.ttodampartners.ttodamttodam.domain.chat.repository.ChatMessageRepository;
import com.ttodampartners.ttodamttodam.domain.chat.repository.ChatroomMemberRepository;
import com.ttodampartners.ttodamttodam.domain.chat.repository.ChatroomRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@RequiredArgsConstructor
@Service
public class ChatroomEnterService {
    private final ChatroomMemberRepository chatroomMemberRepository;
    private final ChatroomRepository chatroomRepository;
    private final ChatMessageRepository chatMessageRepository;

    // 채팅방 입장했을 때, 해당 채팅방 멤버 정보 반환
    @Transactional
    public ChatroomEnterResponse getChatroomDetails(Long chatroomId) {
        List<ChatroomMemberEntity> chatroomMemberEntities = chatroomMemberRepository.findAllByChatroomEntity(
                // 추후 exception handler 처리 필요!!
                chatroomRepository.findByChatroomId(chatroomId).orElseThrow(IllegalArgumentException::new)
        );
        // 채팅방 소속 유저가 한 명도 없을 경우 처리 필요!!

        List<ChatroomProfileResponse> profileList = chatroomMemberEntities.stream().map(
                ChatroomMemberEntity::getChatroomProfile
        ).toList();
        return ChatroomEnterResponse.builder().profiles(profileList).build();
    }

    // 채팅방 메시지 리스트 반환
    @Transactional
    public List<ChatMessageResponse> getChatMessageHistory(Long chatroomId) {
        ChatroomEntity chatroom = chatroomRepository.findByChatroomId(chatroomId).orElseThrow(IllegalArgumentException::new);
        List<ChatMessageEntity> chatMessageEntities = chatMessageRepository.findAllByChatroomEntity(chatroom); // 추후 메시지 하나도 없을 때 경우 처리 필요!!
        List<ChatMessageResponse> chatMessageResponses = chatMessageEntities.stream().map(
                ChatMessageEntity::getChatMessageResponse
        ).toList();
        return chatMessageResponses;
    }
}
