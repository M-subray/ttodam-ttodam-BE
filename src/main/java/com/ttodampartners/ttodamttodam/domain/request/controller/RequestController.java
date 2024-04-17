package com.ttodampartners.ttodamttodam.domain.request.controller;

import com.ttodampartners.ttodamttodam.domain.request.dto.RequestDto;
import com.ttodampartners.ttodamttodam.domain.request.dto.RequestSendDto;
import com.ttodampartners.ttodamttodam.domain.request.entity.RequestEntity;
import com.ttodampartners.ttodamttodam.domain.request.service.RequestService;
import com.ttodampartners.ttodamttodam.global.dto.UserDetailsDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RequiredArgsConstructor
@RestController
public class RequestController {

    private final RequestService requestService;

    @PostMapping("/post/{postId}/request")
    public ResponseEntity<RequestDto> sendRequest(
            @AuthenticationPrincipal UserDetailsDto userDetails,
            @PathVariable Long postId,
            @RequestBody RequestSendDto requestSendDto
        ) {
        Long userId = userDetails.getId();

        return ResponseEntity.ok(RequestDto.of(requestService.sendRequest(userId,postId,requestSendDto)));
       }

    @GetMapping("/post/{postId}/request")
    public ResponseEntity<List<RequestDto>> getRequestList(
            @PathVariable Long postId
    ){
        List<RequestDto> requestList = requestService.getRequestList(postId);
        return ResponseEntity.ok(requestList);
    }

    @PutMapping("/request/{requestId}/{requestStatus}")
    public ResponseEntity<RequestDto> updateRequestStatus(
            @PathVariable Long requestId,
            @PathVariable String requestStatus
    ) {

        return ResponseEntity.ok(RequestDto.of(requestService.updateRequestStatus(requestId, requestStatus)));
    }

}
