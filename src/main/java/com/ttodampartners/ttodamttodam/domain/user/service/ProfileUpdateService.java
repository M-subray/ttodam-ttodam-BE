package com.ttodampartners.ttodamttodam.domain.user.service;

import com.ttodampartners.ttodamttodam.domain.user.dto.ProfileDto;
import com.ttodampartners.ttodamttodam.domain.user.entity.UserEntity;
import com.ttodampartners.ttodamttodam.domain.user.exception.UserException;
import com.ttodampartners.ttodamttodam.domain.user.repository.UserRepository;
import com.ttodampartners.ttodamttodam.domain.user.util.AuthenticationUtil;
import com.ttodampartners.ttodamttodam.domain.user.util.CoordinateFinderUtil;
import com.ttodampartners.ttodamttodam.global.error.ErrorCode;
import java.util.function.Consumer;
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

  @Transactional
  public void profileUpdate (Long userId, ProfileDto profileDto) {
    UserEntity user = getUser(userId);
    isMatchEmail(user);

    if (!user.getNickname().equals(profileDto.getNickname())) {
      existsNickname(profileDto.getNickname());
      updateIfNotNullAndDifferent(user::setNickname,
          profileDto.getNickname(), user.getNickname());
    }

    if (profileDto.getPassword() != null) {
      updateIfNotNullAndDifferent(user::setPassword,
          passwordEncoder.encode(profileDto.getPassword()), user.getPassword());
    }

    if (!user.getLocation().equals(profileDto.getLocation())) {
      double[] coordinates =
          coordinateFinderUtil.getCoordinates(profileDto.getLocation());
      user.setLocationY(coordinates[0]);
      user.setLocationX(coordinates[1]);
      updateIfNotNullAndDifferent(user::setLocation,
          profileDto.getLocation(), user.getLocation());
    }

    if (!user.getPhone().equals(profileDto.getPhone())) {
      existsPhone(profileDto.getPhone());
      updateIfNotNullAndDifferent(user::setPhone,
          profileDto.getPhone(), user.getPhone());
    }

    userRepository.save(user);
  }

  private void updateIfNotNullAndDifferent(Consumer<String> setter,
      String newValue, String oldValue) {
    if (newValue != null && !newValue.equals(oldValue)) {
      setter.accept(newValue);
    }
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

  private UserEntity getUser(Long userId) {
    return userRepository.findById(userId).orElseThrow(() ->
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
