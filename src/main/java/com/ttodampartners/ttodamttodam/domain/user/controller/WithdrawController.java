package com.ttodampartners.ttodamttodam.domain.user.controller;


import com.ttodampartners.ttodamttodam.domain.user.service.WithdrawService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RequiredArgsConstructor
@RestController
public class WithdrawController {
  private final WithdrawService withdrawService;

  @DeleteMapping("/users/{userId}")
  public ResponseEntity<String> withdraw (@PathVariable String userId) {
    withdrawService.withdraw(userId);

    log.info("회원탈퇴 완료, 이메알 : {}", userId);
    return ResponseEntity.ok().body("탈퇴 완료");
  }
}
