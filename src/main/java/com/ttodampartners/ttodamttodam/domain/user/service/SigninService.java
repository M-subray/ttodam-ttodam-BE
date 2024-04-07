package com.ttodampartners.ttodamttodam.domain.user.service;

import com.ttodampartners.ttodamttodam.domain.user.entity.UserEntity;
import com.ttodampartners.ttodamttodam.domain.user.dto.SigninRequestDto;
import com.ttodampartners.ttodamttodam.domain.user.exception.UserException;
import com.ttodampartners.ttodamttodam.domain.user.repository.UserRepository;
import com.ttodampartners.ttodamttodam.global.error.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class SigninService {
  private final PasswordEncoder passwordEncoder;
  private final UserRepository userRepository;

  @Transactional(readOnly = true)
  public void signin(SigninRequestDto signinRequestDto) {
    UserEntity userEntity = getUserByEmail(signinRequestDto.getEmail());

    isMatchPassword(signinRequestDto.getPassword(), userEntity.getPassword());
  }

  private UserEntity getUserByEmail(String email) {
    return userRepository.findByEmail(email).orElseThrow(() ->
        new UserException(ErrorCode.NOT_FOUND_EMAIL));
  }

  private void isMatchPassword (String rawPassword, String encodedPassword) {
    if (!passwordEncoder.matches(rawPassword, encodedPassword)) {
      throw new UserException(ErrorCode.NOT_MATCH_PASSWORD);
    }
  }
}
