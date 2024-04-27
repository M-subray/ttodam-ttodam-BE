package com.ttodampartners.ttodamttodam.domain.user.controller;

import com.ttodampartners.ttodamttodam.domain.user.service.ProfileViewService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class ProfileViewController {
  private final ProfileViewService profileViewService;

  @GetMapping("/users/profiles")
  public ResponseEntity<?> viewProfile() {

    return ResponseEntity.ok(profileViewService.viewProfile());
  }
}
