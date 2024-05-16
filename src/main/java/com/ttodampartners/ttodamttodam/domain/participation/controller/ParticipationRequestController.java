package com.ttodampartners.ttodamttodam.domain.participation.controller;

import com.ttodampartners.ttodamttodam.domain.participation.dto.ParticipationDto;
import com.ttodampartners.ttodamttodam.domain.participation.service.ParticipationRequestService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@RequiredArgsConstructor
public class ParticipationRequestController {

  private final ParticipationRequestService participationRequestService;

  @PostMapping("/post/{postId}/request")
  public ResponseEntity<ParticipationDto> sendRequest(@PathVariable Long postId) {
    ParticipationDto participationDto =
        participationRequestService.sendRequest(postId);

    log.info("요청 전송 성공");
    return ResponseEntity.ok(participationDto);
  }
}
