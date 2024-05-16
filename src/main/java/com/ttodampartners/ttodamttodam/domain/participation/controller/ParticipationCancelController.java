package com.ttodampartners.ttodamttodam.domain.participation.controller;

import static org.springframework.http.HttpStatus.OK;

import com.ttodampartners.ttodamttodam.domain.participation.service.ParticipationCancelService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@RequiredArgsConstructor
public class ParticipationCancelController {

  private final ParticipationCancelService participationCancelService;

  @DeleteMapping("/request/{requestId}")
  public ResponseEntity<Void> cancelParticipationRequest(@PathVariable Long requestId) {

    participationCancelService.cancelParticipationRequest(requestId);
    return ResponseEntity.status(OK).build();
  }
}
