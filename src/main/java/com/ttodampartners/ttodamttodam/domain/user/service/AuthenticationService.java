package com.ttodampartners.ttodamttodam.domain.user.service;

import com.ttodampartners.ttodamttodam.domain.user.entity.UserEntity;
import com.ttodampartners.ttodamttodam.domain.user.exception.UserException;
import com.ttodampartners.ttodamttodam.domain.user.repository.UserRepository;
import com.ttodampartners.ttodamttodam.global.dto.UserDetailsDto;
import com.ttodampartners.ttodamttodam.global.error.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationService implements UserDetailsService {
  private final UserRepository userRepository;

  @Override
  public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
    UserEntity userEntity = findByEmail(email);
    return UserDetailsDto.builder()
        .id(userEntity.getId())
        .email(userEntity.getEmail())
        .password(userEntity.getPassword())
        .build();
  }

  private UserEntity findByEmail(String email) throws UserException {
    return userRepository.findByEmail(email)
        .orElseThrow(() -> new UserException(ErrorCode.NOT_FOUND_EMAIL));
  }

}
