package com.ttodampartners.ttodamttodam.domain.chat.service;

import com.ttodampartners.ttodamttodam.domain.chat.dto.ChatResponse;
import com.ttodampartners.ttodamttodam.domain.chat.dto.request.ChatroomCreateRequest;
import com.ttodampartners.ttodamttodam.domain.chat.dto.response.ChatroomResponse;
import com.ttodampartners.ttodamttodam.domain.chat.dto.response.ChatroomResponseProfile;
import com.ttodampartners.ttodamttodam.domain.chat.entity.ChatroomsEntity;
import com.ttodampartners.ttodamttodam.domain.chat.entity.PostEntity;
import com.ttodampartners.ttodamttodam.domain.chat.entity.UserEntity;
import com.ttodampartners.ttodamttodam.domain.chat.repository.ChatroomsRepository;
import com.ttodampartners.ttodamttodam.domain.chat.repository.PostRepository;
import com.ttodampartners.ttodamttodam.domain.chat.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
public class ChatroomService {
    private final ChatroomsRepository chatroomsRepository;
    private final PostRepository postRepository;
    private final UserRepository userRepository;

    // 일대일 개인 채팅방 생성
    @Transactional
    public ChatroomResponse createChatroom(ChatroomCreateRequest request) {
        // 게시글 문의자
        UserEntity user = userRepository.findByUserId(request.getUserId()).orElseThrow(IllegalArgumentException::new);
        // 관련 게시글
        PostEntity post = postRepository.findByPostId(request.getPostId()).orElseThrow(IllegalArgumentException::new);
        // 게시글 작성자
        UserEntity host = userRepository.findByUserId(post.getUserEntity().getUserId()).orElseThrow(IllegalArgumentException::new);

        ChatroomsEntity chatroom = chatroomsRepository.save(
                ChatroomsEntity.builder().postEntity(post).chatName(post.getTitle()).userCount(2).build());

        // 해당 채팅방에 소속된 유저(공구 주최자, 문의자)의 프로필 정보
        List<ChatroomResponseProfile> profileList = new ArrayList<>();
        profileList.add(ChatroomResponseProfile.builder().userId(host.getUserId()).nickname(host.getNickname()).profileImage(host.getProfileImgUrl()).build());
        profileList.add(ChatroomResponseProfile.builder().userId(user.getUserId()).nickname(user.getNickname()).profileImage(user.getProfileImgUrl()).build());

        return ChatroomResponse.builder()
                .userChatroomId(chatroom.getChatroomId())
                .hostId(post.getUserEntity().getUserId()).userCount(2)
                .chatName(post.getTitle())
                .createdAt(chatroom.getCreatedDate())
                .profiles(profileList)
                .build();
    }
}
