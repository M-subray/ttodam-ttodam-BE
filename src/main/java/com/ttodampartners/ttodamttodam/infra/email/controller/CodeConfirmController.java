package com.ttodampartners.ttodamttodam.infra.email.controller;

import com.ttodampartners.ttodamttodam.infra.email.service.CodeConfirmService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
public class CodeConfirmController {
  private final CodeConfirmService codeConfirmService;

  @GetMapping("/users/signup/receive_code")
  public ResponseEntity<String> receiveCode(
      @RequestParam("email") String email,
      @RequestParam("code") String code) {

    codeConfirmService.codeConfirm(email, code);
    log.info("인증 완료, 이메일 : {}", email);
    return ResponseEntity.ok().body("인증 완료");
  }
}
