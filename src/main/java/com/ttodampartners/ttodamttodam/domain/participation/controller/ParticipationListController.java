package com.ttodampartners.ttodamttodam.domain.participation.controller;

import com.ttodampartners.ttodamttodam.domain.participation.dto.ActivitiesDto;
import com.ttodampartners.ttodamttodam.domain.participation.dto.ParticipationDto;
import com.ttodampartners.ttodamttodam.domain.participation.service.ParticipationListService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
public class ParticipationListController {

  private final ParticipationListService participationListService;

  // 모집에 참여한 게시글 목록 조회 (로그인 유저가 참여요청을 보낸 모든 게시글)
  @GetMapping("/users/activities")
  public ResponseEntity<List<ActivitiesDto>> getParticipationListForCurUser() {
    List<ActivitiesDto> activities =
        participationListService.getParticipationListForCurUser();

    return ResponseEntity.ok(activities);
  }

  @GetMapping("/post/{postId}/request")
  public ResponseEntity<List<ParticipationDto>> getParticipationListFromPost(
      @PathVariable Long postId) {
    List<ParticipationDto> requestList =
        participationListService.getParticipationListFromPost(postId);

    return ResponseEntity.ok(requestList);
  }
}