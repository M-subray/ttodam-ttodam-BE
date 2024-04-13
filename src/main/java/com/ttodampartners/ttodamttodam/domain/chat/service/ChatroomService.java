package com.ttodampartners.ttodamttodam.domain.chat.service;

import com.ttodampartners.ttodamttodam.domain.chat.dto.request.ChatroomCreateRequest;
import com.ttodampartners.ttodamttodam.domain.chat.dto.response.ChatroomResponse;
import com.ttodampartners.ttodamttodam.domain.chat.dto.response.ChatroomResponseProfile;
import com.ttodampartners.ttodamttodam.domain.chat.entity.ChatroomEntity;
import com.ttodampartners.ttodamttodam.domain.chat.entity.ChatroomMemberEntity;
import com.ttodampartners.ttodamttodam.domain.chat.repository.ChatroomMemberRepository;
import com.ttodampartners.ttodamttodam.domain.post.entity.PostEntity;
import com.ttodampartners.ttodamttodam.domain.user.entity.UserEntity;
import com.ttodampartners.ttodamttodam.domain.chat.repository.ChatroomRepository;
import com.ttodampartners.ttodamttodam.domain.post.repository.PostRepository;
import com.ttodampartners.ttodamttodam.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class ChatroomService {
    private final PostRepository postRepository;
    private final UserRepository userRepository;
    private final ChatroomRepository chatroomRepository;
    private final ChatroomMemberRepository chatroomMemberRepository;

    // 일대일 개인 채팅방 생성
    @Transactional
    public ChatroomResponse createChatroom(ChatroomCreateRequest request) {
        // 문의자
        UserEntity user = userRepository.findByUserId(request.getUserId()).orElseThrow(IllegalArgumentException::new);

        PostEntity post = postRepository.findByPostId(request.getPostId()).orElseThrow(IllegalArgumentException::new);
        // 게시글 작성자
        UserEntity host = userRepository.findByUserId(post.getUserEntity().getUserId()).orElseThrow(IllegalArgumentException::new);

        // CHATROOM 테이블에 컬럼 추가
        ChatroomEntity chatroom = chatroomRepository.save(
                ChatroomEntity.builder().postEntity(post).chatName(post.getTitle()).userCount(2).build());
        // CHATROOM_MEMBER 테이블에 컬럼 추가
        List<UserEntity> members = new ArrayList<>(
                Arrays.asList(user, host)
        );
        List<ChatroomMemberEntity> memberEntityList = members.stream().map(
                member -> chatroomMemberRepository.save(
                        ChatroomMemberEntity.builder()
                                .chatroomEntity(chatroom)
                                .userEntity(member)
                                .build()))
                .toList();


        // 해당 채팅방에 소속된 유저(공구 주최자, 문의자)의 프로필 정보
        List<ChatroomResponseProfile> profileList = new ArrayList<>();
        profileList.add(ChatroomResponseProfile.builder().userId(host.getUserId()).nickname(host.getNickname()).profileImage(host.getProfileImgUrl()).build());
        profileList.add(ChatroomResponseProfile.builder().userId(user.getUserId()).nickname(user.getNickname()).profileImage(user.getProfileImgUrl()).build());

        return ChatroomResponse.builder()
                .userChatroomId(chatroom.getChatroomId())
                .hostId(post.getUserEntity().getUserId()).userCount(2)
                .chatName(post.getTitle())
                .createdAt(chatroom.getCreateAt())
                .profiles(profileList)
                .build();
    }
}
