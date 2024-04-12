package com.ttodampartners.ttodamttodam.domain.chat.service;

import com.ttodampartners.ttodamttodam.domain.chat.dto.request.ChatroomCreateRequest;
import com.ttodampartners.ttodamttodam.domain.chat.dto.response.ChatroomResponse;
import com.ttodampartners.ttodamttodam.domain.chat.entity.ChatroomsEntity;
import com.ttodampartners.ttodamttodam.domain.chat.entity.PostEntity;
import com.ttodampartners.ttodamttodam.domain.chat.entity.UserEntity;
import com.ttodampartners.ttodamttodam.domain.chat.repository.ChatroomsRepository;
import com.ttodampartners.ttodamttodam.domain.chat.repository.PostRepository;
import com.ttodampartners.ttodamttodam.domain.chat.repository.UserRepository;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Service
public class ChatroomService {
    private final ChatroomsRepository chatroomsRepository;
    private final PostRepository postRepository;
    private final UserRepository userRepository;

    @Transactional
    public ChatroomResponse createChatroom(ChatroomCreateRequest request) {
        UserEntity user = userRepository.findByUserId(request.getUserId()).orElseThrow(IllegalArgumentException::new);
        PostEntity post = postRepository.findByPostId(request.getPostId()).orElseThrow(IllegalArgumentException::new);

//        Long chatroomId = chatroomsRepository.save(
//                ChatroomsEntity.builder().chatName(post.getTitle()).userCount(2).build()).getChatroomId();
        post.createChatroom(post.getTitle(), 2);

        return new ChatroomResponse(1L, user.getUserId()); // 임시! userChatroomId 수정 필요!!
    }
}
