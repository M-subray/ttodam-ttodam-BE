package com.ttodampartners.ttodamttodam.domain.user.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
@RequiredArgsConstructor
public class KakaoRedirectController {
  @Value("${oauth2.kakao.client-id}")
  private String clientId;
  @Value("${oauth2.kakao.redirect-uri}")
  private String redirectUrl;

  @GetMapping("/users/signin/kakao")
  public ResponseEntity<?> kakaoSignin () {
    String authUrl = UriComponentsBuilder.fromHttpUrl("https://kauth.kakao.com/oauth/authorize")
        .queryParam("client_id", clientId)
        .queryParam("redirect_uri", redirectUrl)
        .queryParam("response_type", "code")
        .toUriString();

    return ResponseEntity.status(HttpStatus.FOUND).header("Location", authUrl).build();
  }
}
