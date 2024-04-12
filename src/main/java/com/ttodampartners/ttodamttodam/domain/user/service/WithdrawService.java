package com.ttodampartners.ttodamttodam.domain.user.service;

import com.ttodampartners.ttodamttodam.domain.user.entity.UserEntity;
import com.ttodampartners.ttodamttodam.domain.user.exception.UserException;
import com.ttodampartners.ttodamttodam.domain.user.repository.UserRepository;
import com.ttodampartners.ttodamttodam.domain.user.util.AuthenticationUtil;
import com.ttodampartners.ttodamttodam.global.error.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class WithdrawService {

  private final UserRepository userRepository;

  public void withdraw(String userId) {
    UserEntity User = getUser(userId);
    isMatchEmail(userId);

    userRepository.delete(User);
  }

  private UserEntity getUser(String userId) {
    return userRepository.findByEmail(userId).orElseThrow(() ->
        new UserException(ErrorCode.NOT_FOUND_EMAIL));
  }

  private void isMatchEmail(String userId) {
    Authentication authentication = AuthenticationUtil.getAuthentication();
    String authEmail = authentication.getName();

    if (!authEmail.equals(userId)) {
      throw new UserException(ErrorCode.PERMISSION_DENIED);
    }
  }
}
