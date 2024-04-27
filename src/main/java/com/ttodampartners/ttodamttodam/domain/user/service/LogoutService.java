package com.ttodampartners.ttodamttodam.domain.user.service;

import com.ttodampartners.ttodamttodam.domain.notification.service.NotificationService;
import com.ttodampartners.ttodamttodam.domain.user.entity.UserEntity;
import com.ttodampartners.ttodamttodam.domain.user.exception.UserException;
import com.ttodampartners.ttodamttodam.domain.user.repository.UserRepository;
import com.ttodampartners.ttodamttodam.domain.user.util.AuthenticationUtil;
import com.ttodampartners.ttodamttodam.global.config.security.TokenProvider;
import com.ttodampartners.ttodamttodam.global.error.ErrorCode;
import com.ttodampartners.ttodamttodam.global.util.RedisUtil;
import java.time.Duration;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
@RequiredArgsConstructor
public class LogoutService {

  private final RedisUtil redisUtil;
  private final TokenProvider tokenProvider;
  private final UserRepository userRepository;
  private final NotificationService notificationService;

  @Transactional
  public void addTokenToBlacklist(String token) {
    long remainingTime =
        tokenProvider.calculateRemainingTime(tokenProvider.parseClaims(token).getExpiration());
    redisUtil.setValues(token, "BLACKLISTED", Duration.ofMillis(remainingTime));
    // 로그아웃과 동시에 SSE 연결 종료
    notificationService.remove(getUser().getId());
  }

  private UserEntity getUser () {
    Authentication authentication = AuthenticationUtil.getAuthentication();
    return userRepository.findByEmail(authentication.getName()).orElseThrow(() ->
        new UserException(ErrorCode.NOT_FOUND_USER));
  }
}