package com.ttodampartners.ttodamttodam.domain.chat.controller;

import com.ttodampartners.ttodamttodam.domain.chat.dto.request.ChatroomCreateRequest;
import com.ttodampartners.ttodamttodam.domain.chat.dto.response.ChatroomListResponse;
import com.ttodampartners.ttodamttodam.domain.chat.dto.response.ChatroomResponse;
import com.ttodampartners.ttodamttodam.domain.chat.service.ChatroomService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@RequestMapping("/chatroom")
@RestController
public class ChatroomController {
    private final ChatroomService chatroomService;

    @PostMapping // POST /chatroom (채팅방 생성)
    public ResponseEntity<ChatroomResponse> createChatroom(@RequestBody ChatroomCreateRequest request) {
        ChatroomResponse chatroomResponse = chatroomService.createChatroom(request);
         // ChatResponse 공통 응답 사용한다면 return 이렇게
         // return ResponseEntity.ok(ChatResponse.res(HttpStatus.OK, HttpStatus.OK.toString(), chatroomResponse));
        return ResponseEntity.ok(chatroomResponse);
    }

    @GetMapping // GET /chatroom/{userId} (채팅방 목록 조회)
    public ResponseEntity<List<ChatroomListResponse>> getChatrooms(@RequestParam Long userId) {
        // 수정 필요!!!!
        List<ChatroomListResponse> chatroomListResponses = new ArrayList<>();
        return ResponseEntity.ok(chatroomListResponses);
    }
}
