package com.ttodampartners.ttodamttodam.domain.participation.controller;

import com.ttodampartners.ttodamttodam.domain.participation.dto.ParticipationDto;
import com.ttodampartners.ttodamttodam.domain.participation.service.ParticipationUpdateService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@RequiredArgsConstructor
public class ParticipationUpdateController {
  private final ParticipationUpdateService participationUpdateService;

  @PutMapping("/request/{requestId}/{requestStatus}")
  public ResponseEntity<ParticipationDto> updateRequestStatus(
      @PathVariable Long requestId,
      @PathVariable String requestStatus) {
    ParticipationDto participationDto =
        participationUpdateService.updateRequestStatus(requestId, requestStatus);

    return ResponseEntity.ok(participationDto);
  }
}
