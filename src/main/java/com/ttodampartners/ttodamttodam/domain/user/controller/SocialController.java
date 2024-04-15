package com.ttodampartners.ttodamttodam.domain.user.controller;

import com.ttodampartners.ttodamttodam.domain.user.service.SocialService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
public class SocialController {

  private final SocialService socialService;

  @GetMapping("/login/oauth2/code/{registrationId}")
  public ResponseEntity<?> socialLogin (@RequestParam String code,
      @PathVariable String registrationId) {
    String token = socialService.socialLogin(code, registrationId);

    HttpHeaders headers = new HttpHeaders();
    headers.add("Authorization", "Bearer " + token);
    log.info("소셜 로그인 성공");

    return ResponseEntity.ok().headers(headers).body("로그인 성공");
  }
}

