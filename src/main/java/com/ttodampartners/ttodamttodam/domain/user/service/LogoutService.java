package com.ttodampartners.ttodamttodam.domain.user.service;

import com.ttodampartners.ttodamttodam.domain.notification.service.NotificationService;
import com.ttodampartners.ttodamttodam.domain.user.entity.UserEntity;
import com.ttodampartners.ttodamttodam.global.util.UserUtil;
import com.ttodampartners.ttodamttodam.global.config.security.TokenProvider;
import com.ttodampartners.ttodamttodam.global.util.RedisUtil;
import java.time.Duration;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
@RequiredArgsConstructor
public class LogoutService {

  private final RedisUtil redisUtil;
  private final TokenProvider tokenProvider;
  private final UserUtil userUtil;
  private final NotificationService notificationService;

  @Transactional
  public void addTokenToBlacklist(String token) {
    UserEntity user = userUtil.getCurUserEntity();

    long remainingTime =
        tokenProvider.calculateRemainingTime(tokenProvider.parseClaims(token).getExpiration());
    redisUtil.setValues(token, "BLACKLISTED", Duration.ofMillis(remainingTime));
    // 로그아웃과 동시에 SSE 연결 종료
    notificationService.remove(user.getId());
  }
}