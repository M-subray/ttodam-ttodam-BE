package com.ttodampartners.ttodamttodam.domain.chat.service;

import com.ttodampartners.ttodamttodam.domain.chat.dto.ChatExceptionResponse;
import com.ttodampartners.ttodamttodam.domain.chat.dto.response.ChatroomListResponse;
import com.ttodampartners.ttodamttodam.domain.chat.entity.ChatroomMemberEntity;
import com.ttodampartners.ttodamttodam.domain.chat.exception.ChatroomException;
import com.ttodampartners.ttodamttodam.domain.chat.repository.ChatroomMemberRepository;
import com.ttodampartners.ttodamttodam.domain.user.entity.UserEntity;
import com.ttodampartners.ttodamttodam.domain.user.exception.UserException;
import com.ttodampartners.ttodamttodam.domain.user.repository.UserRepository;
import com.ttodampartners.ttodamttodam.global.error.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.List;

import static com.ttodampartners.ttodamttodam.global.error.ErrorCode.NOT_FOUND_USER;
import static com.ttodampartners.ttodamttodam.global.error.ErrorCode.USER_CHATROOM_NOT_EXIST;

@RequiredArgsConstructor
@Service
public class ChatroomListService {
    private final UserRepository userRepository;
    private final ChatroomMemberRepository chatroomMemberRepository;

    // 유저가 속한 채팅방 목록 조회 -> List 반환
    @Transactional
    public List<ChatroomListResponse> getChatrooms(Long userId) {
        UserEntity user = userRepository.findById(userId).orElseThrow(() -> new UserException(NOT_FOUND_USER));
        // 유저가 속한 CHATROOM_MEMBER 엔티티 리스트
        List<ChatroomMemberEntity> userChatrooms = chatroomMemberRepository.findAllByUserEntity(user);

        if (CollectionUtils.isEmpty(userChatrooms)) {
            ErrorCode code = USER_CHATROOM_NOT_EXIST;
            throw new ChatroomException(code, ChatExceptionResponse.res(HttpStatus.NOT_FOUND, code.getDescription()));
        }

        return userChatrooms.stream().map(ChatroomMemberEntity::getChatroomInfos).toList();
    }
}
