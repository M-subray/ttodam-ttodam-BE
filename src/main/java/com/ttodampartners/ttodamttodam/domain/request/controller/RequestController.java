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

import static org.springframework.http.HttpStatus.OK;


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
            @AuthenticationPrincipal UserDetailsDto userDetails,
            @PathVariable Long postId
    ){
        Long userId = userDetails.getId();
        List<RequestDto> requestList = requestService.getRequestList(userId,postId);
        return ResponseEntity.ok(requestList);
    }

    @PutMapping("/request/{requestId}/{requestStatus}")
    public ResponseEntity<RequestDto> updateRequestStatus(
            @AuthenticationPrincipal UserDetailsDto userDetails,
            @PathVariable Long requestId,
            @PathVariable String requestStatus
    ) {
        Long userId = userDetails.getId();
        return ResponseEntity.ok(RequestDto.of(requestService.updateRequestStatus(userId, requestId, requestStatus)));
    }

    @DeleteMapping("/request/{requestId}")
    public ResponseEntity<Void> deleteRequest(
            @AuthenticationPrincipal UserDetailsDto userDetails,
            @PathVariable Long requestId
    )
    {
        Long userId = userDetails.getId();
        requestService.deleteRequest(userId, requestId);
        return ResponseEntity.status(OK).build();
    }

}
