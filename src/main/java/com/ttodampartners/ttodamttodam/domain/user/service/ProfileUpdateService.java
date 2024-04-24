package com.ttodampartners.ttodamttodam.domain.user.service;

import com.ttodampartners.ttodamttodam.domain.user.dto.ProfileDto;
import com.ttodampartners.ttodamttodam.domain.user.entity.UserEntity;
import com.ttodampartners.ttodamttodam.domain.user.exception.UserException;
import com.ttodampartners.ttodamttodam.domain.user.repository.UserRepository;
import com.ttodampartners.ttodamttodam.domain.user.util.AuthenticationUtil;
import com.ttodampartners.ttodamttodam.domain.user.util.CoordinateFinderUtil;
import com.ttodampartners.ttodamttodam.global.error.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class ProfileUpdateService {
  private final UserRepository userRepository;
  private final PasswordEncoder passwordEncoder;
  private final CoordinateFinderUtil coordinateFinderUtil;

  /*
  (비밀번호 이외의 값들)
  수정할 때의 정보들이 기존과 같다면 DB 와의 연결을 피하기 위해
  기존의 값 != 새로운 값 일 때만 업데이트
   */
  @Transactional
  public void profileUpdate (ProfileDto profileDto) {
    UserEntity user = getUser();
    isMatchEmail(user);

    if (!profileDto.getNickname().equals(user.getNickname())) {
      existsNickname(profileDto.getNickname());
      user.setNickname(profileDto.getNickname());
    }

    /*
    비밀번호는 조회때 기입돼 있지 않기 때문에 null 이 아닌 경우 수정의 의도가 있는 것으로
    판단해 수정 로직 진행
     */
    if (profileDto.getPassword() != null) {
      // 소셜 계정은 비밀번호 수정 불가 (기존 비밀번호가 null 인경우 소셜 계정임)
      if (user.getPassword() == null) {
        throw new UserException(ErrorCode.SOCIAL_ACCOUNTS_IMPOSSIBLE);
      }
      if (!user.getPassword().equals(passwordEncoder.encode(profileDto.getPassword()))) {
        user.setPassword(passwordEncoder.encode(profileDto.getPassword()));
      }
    }

    if (!profileDto.getLocation().equals(user.getLocation())) {
      double[] coordinates =
          coordinateFinderUtil.getCoordinates(profileDto.getLocation());
      user.setLocationY(coordinates[0]);
      user.setLocationX(coordinates[1]);
      user.setLocation(profileDto.getLocation());
    }

    if (!profileDto.getPhone().equals(user.getPhone())) {
      existsPhone(profileDto.getPhone());
      user.setPhone(profileDto.getPhone());
    }

    userRepository.save(user);
  }

  private void existsNickname(String nickname) {
    if (userRepository.existsByNickname(nickname)) {
      throw new UserException(ErrorCode.EXISTS_NICKNAME);
    }
  }

  private void existsPhone(String phone) {
    if (userRepository.existsByPhone(phone)) {
      throw new UserException(ErrorCode.EXISTS_PHONE);
    }
  }

  private UserEntity getUser () {
    Authentication authentication = AuthenticationUtil.getAuthentication();
    return userRepository.findByEmail(authentication.getName()).orElseThrow(() ->
        new UserException(ErrorCode.NOT_FOUND_USER));
  }

  private void isMatchEmail(UserEntity user) {
    Authentication authentication = AuthenticationUtil.getAuthentication();
    String authEmail = authentication.getName();

    if (!authEmail.equals(user.getEmail())) {
      throw new UserException(ErrorCode.PERMISSION_DENIED);
    }
  }
}
