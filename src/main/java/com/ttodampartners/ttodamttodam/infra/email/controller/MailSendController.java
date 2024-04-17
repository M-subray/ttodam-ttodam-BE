package com.ttodampartners.ttodamttodam.infra.email.controller;

import com.ttodampartners.ttodamttodam.infra.email.service.MailSendService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@RequiredArgsConstructor
public class MailSendController {
  private final MailSendService mailSendService;

  @PostMapping("/users/signup/sendmail")
  public ResponseEntity<String> sendMail(@RequestParam String email) {
    mailSendService.sendEmail(email);

    log.info("메일 발송 성공, 이메일 : {}", email);
    return ResponseEntity.ok().body("메일을 확인해 주세요.");
  }
}