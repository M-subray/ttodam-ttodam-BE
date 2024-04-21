package com.ttodampartners.ttodamttodam.domain.chat.controller;

import com.ttodampartners.ttodamttodam.domain.chat.dto.response.ChatroomEnterResponse;
import com.ttodampartners.ttodamttodam.domain.chat.service.ChatroomEnterService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


@Slf4j
@RequiredArgsConstructor
@RequestMapping("/chatroom")
@RestController
public class ChatroomEnterController {
    private final ChatroomEnterService chatroomEnterService;

    // {userChatroomId} 채팅방 입장 시 채팅방에 속한 유저들 프로필 리스트 반환
    @GetMapping // GET /chatroom/{userChatroomId}
    public ResponseEntity<ChatroomEnterResponse> getChatroomDetails(@RequestParam Long userChatroomId) {
        ChatroomEnterResponse chatroomEnterResponse = chatroomEnterService.getChatroomDetails(userChatroomId);
        return ResponseEntity.ok(chatroomEnterResponse);
    }
}
