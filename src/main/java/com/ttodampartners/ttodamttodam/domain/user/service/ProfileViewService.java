package com.ttodampartners.ttodamttodam.domain.user.service;

import com.ttodampartners.ttodamttodam.domain.user.dto.ProfileViewDto;
import com.ttodampartners.ttodamttodam.domain.user.entity.UserEntity;
import com.ttodampartners.ttodamttodam.domain.user.exception.UserException;
import com.ttodampartners.ttodamttodam.domain.user.repository.UserRepository;
import com.ttodampartners.ttodamttodam.domain.user.util.AuthenticationUtil;
import com.ttodampartners.ttodamttodam.global.error.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProfileViewService {
  private final UserRepository userRepository;
  public ProfileViewDto viewProfile () {
    UserEntity user = getUser();

    return ProfileViewDto.builder()
        .nickname(user.getNickname())
        .profileImgUrl(user.getProfileImgUrl())
        .manners(user.getManners())
        .build();
  }

  private UserEntity getUser () {
    Authentication authentication = AuthenticationUtil.getAuthentication();
    return userRepository.findByEmail(authentication.getName()).orElseThrow(() ->
        new UserException(ErrorCode.NOT_FOUND_USER));
  }
}
