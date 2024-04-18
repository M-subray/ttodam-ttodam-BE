package com.ttodampartners.ttodamttodam.domain.user.controller;

import com.ttodampartners.ttodamttodam.domain.user.dto.ProfileDto;
import com.ttodampartners.ttodamttodam.domain.user.service.ProfileEditService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RequiredArgsConstructor
@RestController
public class ProfileEditController {
  private final ProfileEditService profileEditService;

  @GetMapping("users/{userId}/profiles/update")
  public ResponseEntity<ProfileDto> getProfile(
      @PathVariable Long userId) {
    ProfileDto profile = profileEditService.getProfile(userId);

    return ResponseEntity.ok(profile);
  }
}
