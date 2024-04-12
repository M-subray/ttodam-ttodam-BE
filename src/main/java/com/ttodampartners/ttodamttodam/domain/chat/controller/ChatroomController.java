package com.ttodampartners.ttodamttodam.domain.chat.controller;

import com.ttodampartners.ttodamttodam.domain.chat.dto.ChatResponse;
import com.ttodampartners.ttodamttodam.domain.chat.dto.request.ChatroomCreateRequest;
import com.ttodampartners.ttodamttodam.domain.chat.dto.response.ChatroomResponse;
import com.ttodampartners.ttodamttodam.domain.chat.service.ChatroomService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RequiredArgsConstructor
@RequestMapping("/chatroom")
@RestController
public class ChatroomController {
    private final ChatroomService chatroomService;

    @PostMapping
    public ResponseEntity<ChatResponse<ChatroomResponse>> createChatroom(@RequestBody ChatroomCreateRequest request) {
        ChatroomResponse chatroomResponse = chatroomService.createChatroom(request);
        return ResponseEntity.ok(ChatResponse.res(HttpStatus.OK, HttpStatus.OK.toString(), chatroomResponse));
    }
}
