package com.ttodampartners.ttodamttodam.domain.user.service;

import com.ttodampartners.ttodamttodam.domain.user.dto.ProfileViewDto;
import com.ttodampartners.ttodamttodam.domain.user.entity.UserEntity;
import com.ttodampartners.ttodamttodam.global.util.UserUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProfileViewService {
  private final UserUtil userUtil;
  public ProfileViewDto viewProfile () {
    UserEntity user = userUtil.getCurUserEntity();

    return ProfileViewDto.builder()
        .nickname(user.getNickname())
        .profileImgUrl(user.getProfileImgUrl())
        .manners(user.getManners())
        .build();
  }

}
