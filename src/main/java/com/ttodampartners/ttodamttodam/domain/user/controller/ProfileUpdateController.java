package com.ttodampartners.ttodamttodam.domain.user.controller;

import com.ttodampartners.ttodamttodam.domain.user.dto.ProfileDto;
import com.ttodampartners.ttodamttodam.domain.user.service.ProfileUpdateService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RequiredArgsConstructor
@RestController
public class ProfileUpdateController {
  private final ProfileUpdateService profileUpdateService;

  @PutMapping("users/{userId}/profiles/update")
  public ResponseEntity<String> profileUpdate (
      @PathVariable Long userId,
      @RequestBody @Valid ProfileDto profileDto) {

    profileUpdateService.profileUpdate(userId, profileDto);

    log.info("프로필 수정 성공, userId : {}", userId);
    return ResponseEntity.ok().body("프로필 수정 성공");
  }
}
