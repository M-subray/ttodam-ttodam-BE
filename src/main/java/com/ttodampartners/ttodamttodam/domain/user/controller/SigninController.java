package com.ttodampartners.ttodamttodam.domain.user.controller;

import com.ttodampartners.ttodamttodam.domain.user.dto.SigninRequestDto;
import com.ttodampartners.ttodamttodam.domain.user.service.SigninService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
public class SigninController {
  private final SigninService signinService;

  @PostMapping("/users/signin")
  public ResponseEntity<?> signin (@RequestBody @Valid SigninRequestDto signinRequestDto) {
    String token = signinService.signin(signinRequestDto);

    HttpHeaders headers = new HttpHeaders();
    headers.add("Authorization", "Bearer " + token);
    log.info("로그인 성공, email : {}", signinRequestDto.getEmail());

    return ResponseEntity.ok().headers(headers).body("로그인 성공");
  }
}