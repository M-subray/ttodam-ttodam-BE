package com.ttodampartners.ttodamttodam.domain.chat.service;

import com.ttodampartners.ttodamttodam.domain.chat.dto.ChatExceptionResponse;
import com.ttodampartners.ttodamttodam.domain.chat.dto.request.ChatroomCreateRequest;
import com.ttodampartners.ttodamttodam.domain.chat.dto.response.ChatroomListResponse;
import com.ttodampartners.ttodamttodam.domain.chat.dto.response.ChatroomResponse;
import com.ttodampartners.ttodamttodam.domain.chat.dto.response.ChatroomProfileResponse;
import com.ttodampartners.ttodamttodam.domain.chat.entity.ChatroomEntity;
import com.ttodampartners.ttodamttodam.domain.chat.entity.ChatroomMemberEntity;
import com.ttodampartners.ttodamttodam.domain.chat.exception.ChatroomException;
import com.ttodampartners.ttodamttodam.domain.chat.exception.ChatroomExistedException;
import com.ttodampartners.ttodamttodam.domain.chat.dto.response.ChatroomExistedResponseBody;
import com.ttodampartners.ttodamttodam.domain.chat.repository.ChatroomMemberRepository;
import com.ttodampartners.ttodamttodam.domain.chat.repository.ChatroomRepository;
import com.ttodampartners.ttodamttodam.domain.post.entity.PostEntity;
import com.ttodampartners.ttodamttodam.domain.post.repository.PostRepository;
import com.ttodampartners.ttodamttodam.domain.user.entity.UserEntity;
import com.ttodampartners.ttodamttodam.domain.user.repository.UserRepository;
import com.ttodampartners.ttodamttodam.domain.user.exception.UserException;
import com.ttodampartners.ttodamttodam.global.error.ErrorCode;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.ttodampartners.ttodamttodam.global.error.ErrorCode.*;
import static java.lang.String.format;

@RequiredArgsConstructor
@Service
public class ChatroomService {
    private final PostRepository postRepository;
    private final UserRepository userRepository;
    private final ChatroomRepository chatroomRepository;
    private final ChatroomMemberRepository chatroomMemberRepository;

    // 일대일 개인 채팅방 생성 -> response body 반환
    // 추후 게시글 상태가 모집중인지 체크!!
    @Transactional
    public ChatroomResponse createChatroom(@Valid ChatroomCreateRequest request) {
        UserEntity user = userRepository.findById(request.getUserId()).orElseThrow(() -> new UserException(NOT_FOUND_USER)); // 문의자

        PostEntity post = postRepository.findById(request.getPostId()).orElseThrow(() -> new IllegalArgumentException("postId의 게시글이 존재하지 않습니다."));

        UserEntity host = userRepository.findById(post.getUser().getId()).orElseThrow(IllegalArgumentException::new); // 게시글 작성자

        // 이미 user가 해당 post에 일대일 채팅방 생성한 적 있는지 체크
        List<ChatroomEntity> chatroomEntities = chatroomRepository.findByPostEntity(post); // 이 post에서 생성된 채팅방 리스트
        ErrorCode code = CHATROOM_ALREADY_EXIST;
        if (!CollectionUtils.isEmpty(chatroomEntities)) { // 이 post에서 생성된 채팅방이 하나라도 존재한다면
            for (ChatroomEntity chatroom: chatroomEntities) {
                // 추후 chat_active도 체크!!
                if (chatroomMemberRepository.existsByUserEntityAndChatroomEntity(user, chatroom)) {
                    throw new ChatroomExistedException(
                            code,
                            ChatExceptionResponse.res(
                                    HttpStatus.BAD_REQUEST,
                                    code.getDescription(),
                                    ChatroomExistedResponseBody.builder().chatroomId(chatroom.getChatroomId()).build()
                            )
                    );
                }
            }
        };

        // CHATROOM 테이블에 컬럼 추가
        ChatroomEntity chatroom = chatroomRepository.save(
                ChatroomEntity.builder().postEntity(post).chatName(post.getTitle()).userCount(2).build()
        );

        // 채팅방 소속 멤버들
        List<UserEntity> members = new ArrayList<>(
                Arrays.asList(user, host)
        );
        // CHATROOM_MEMBER 테이블에 컬럼 추가
        List<ChatroomMemberEntity> memberEntityList = saveChatroomMembers(members, chatroom);
        // 해당 채팅방에 소속된 유저(공구 주최자, 문의자)의 프로필 정보 리스트 받아오기
        List<ChatroomProfileResponse> profileList = getChatroomProfiles(members);

        return ChatroomResponse.builder()
                .chatroomId(chatroom.getChatroomId())
                .hostId(post.getUser().getId()).userCount(2)
                .chatName(post.getTitle())
                .createAt(chatroom.getCreateAt())
                .profiles(profileList)
                .build();
    }

    // 유저가 속한 채팅방 목록 조회
    @Transactional
    public List<ChatroomListResponse> getChatrooms(Long userId) {
        UserEntity user = userRepository.findById(userId).orElseThrow(() -> new UserException(NOT_FOUND_USER));
        // 유저가 속한 CHATROOM_MEMBER 엔티티 리스트
        List<ChatroomMemberEntity> userChatrooms = chatroomMemberRepository.findAllByUserEntity(user);

        if (CollectionUtils.isEmpty(userChatrooms)) {
            throw new ChatroomException(USER_CHATROOM_NOT_EXIST);
        }

        List<ChatroomListResponse> chatroomListResponses = userChatrooms.stream().map(
                ChatroomMemberEntity::getChatroomInfos
        ).toList();

        return chatroomListResponses;
    }

    // 유저가 속한 chatroomId 채팅방 나가기
    @Transactional
    public void leaveChatroom(Long chatroomId, Long userId) {
        UserEntity user = userRepository.findById(userId).orElseThrow(() -> new UserException(NOT_FOUND_USER));
        ChatroomEntity chatroom = chatroomRepository.findByChatroomId(chatroomId).orElseThrow(() -> new ChatroomException(CHATROOM_NOT_EXIST));

        ChatroomMemberEntity userChatroom = chatroomMemberRepository.findByUserEntityAndChatroomEntity(user, chatroom).orElseThrow(() -> new ChatroomException(USER_NOT_IN_CHATROOM));

        chatroomMemberRepository.delete(userChatroom);
    }

    /*
        채팅방에 소속된 유저들 관련 메소드
    */
    @Transactional
    public List<ChatroomMemberEntity> saveChatroomMembers(List<UserEntity> members, ChatroomEntity chatroom) {
        return members.stream().map(
                        member -> chatroomMemberRepository.save(
                                ChatroomMemberEntity.builder()
                                        .chatroomEntity(chatroom)
                                        .userEntity(member)
                                        .build()))
                .toList();
    }

    @Transactional
    public List<ChatroomProfileResponse> getChatroomProfiles(List<UserEntity> members) {
        return members.stream().map(
                member -> ChatroomProfileResponse.builder()
                        .userId(member.getId())
                        .nickname(member.getNickname())
                        .profileImage(member.getProfileImgUrl())
                        .build()

        ).toList();
    }
}
