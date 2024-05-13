package com.ttodampartners.ttodamttodam.domain.user.service;

import com.ttodampartners.ttodamttodam.domain.notification.service.NotificationService;
import com.ttodampartners.ttodamttodam.domain.user.entity.UserEntity;
import com.ttodampartners.ttodamttodam.domain.user.exception.UserException;
import com.ttodampartners.ttodamttodam.domain.user.repository.UserRepository;
import com.ttodampartners.ttodamttodam.global.util.UserUtil;
import com.ttodampartners.ttodamttodam.global.error.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class WithdrawService {
  private final UserUtil userUtil;
  private final UserRepository userRepository;
  private final NotificationService notificationService;

  public void withdraw() {
    UserEntity user = userUtil.getCurUserEntity();
    isMatchEmail(user);

    userRepository.delete(user);
    // 회원탈퇴와 동시에 SSE 연결 종료
    notificationService.remove(user.getId());
  }

  private void isMatchEmail(UserEntity user) {
    Authentication authentication = UserUtil.getAuthentication();
    String authEmail = authentication.getName();

    if (!authEmail.equals(user.getEmail())) {
      throw new UserException(ErrorCode.PERMISSION_DENIED);
    }
  }
}
