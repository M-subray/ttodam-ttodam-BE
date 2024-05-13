package com.ttodampartners.ttodamttodam.global.util;

import com.ttodampartners.ttodamttodam.domain.user.entity.UserEntity;
import com.ttodampartners.ttodamttodam.domain.user.exception.UserException;
import com.ttodampartners.ttodamttodam.domain.user.repository.UserRepository;
import com.ttodampartners.ttodamttodam.global.error.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserUtil {

  private final UserRepository userRepository;

  public UserEntity getCurUserEntity () {
    Authentication authentication = getAuthentication();
    return userRepository.findByEmail(authentication.getName()).orElseThrow(() ->
        new UserException(ErrorCode.NOT_FOUND_USER));
  }

  public UserEntity findUserEntity (Long userId) {
    return userRepository.findById(userId).orElseThrow(() ->
        new UserException(ErrorCode.NOT_FOUND_USER));
  }

  public static Authentication getAuthentication () {
    Authentication authentication =
        SecurityContextHolder.getContext().getAuthentication();

    if (!(authentication.getPrincipal() instanceof UserDetails)) {
      throw new UserException(ErrorCode.SIGNIN_TIME_OUT);
    }

    return authentication;
  }
}
