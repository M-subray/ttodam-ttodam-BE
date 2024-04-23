package com.ttodampartners.ttodamttodam.domain.keyword.service;

import com.ttodampartners.ttodamttodam.domain.keyword.dto.KeywordCreateRequestDto;
import com.ttodampartners.ttodamttodam.domain.keyword.dto.KeywordCreateResponseDto;
import com.ttodampartners.ttodamttodam.domain.keyword.entity.KeywordEntity;
import com.ttodampartners.ttodamttodam.domain.keyword.repository.KeywordRepository;
import com.ttodampartners.ttodamttodam.domain.user.entity.UserEntity;
import com.ttodampartners.ttodamttodam.domain.user.exception.UserException;
import com.ttodampartners.ttodamttodam.domain.user.repository.UserRepository;
import com.ttodampartners.ttodamttodam.domain.user.util.AuthenticationUtil;
import com.ttodampartners.ttodamttodam.global.error.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class KeywordCreateService {
  private final KeywordRepository keywordRepository;
  private final UserRepository userRepository;

  @Transactional
  public KeywordCreateResponseDto createKeyword (KeywordCreateRequestDto keywordName) {

    KeywordEntity keywordEntity = KeywordEntity.builder()
        .user(getUser())
        .keywordName(keywordName.getKeywordName())
        .build();

    return saveKeyword(keywordEntity);
  }

  private KeywordCreateResponseDto saveKeyword (KeywordEntity keywordEntity) {
    // KeywordEntity 저장
    keywordEntity = keywordRepository.save(keywordEntity);
    // 저장된 KeywordEntity 를 KeywordDto 에 다시 변환

    return KeywordCreateResponseDto.builder()
        .id(keywordEntity.getId())
        .keywordName(keywordEntity.getKeywordName())
        .build();
  }

  private UserEntity getUser() {
    Authentication authentication = AuthenticationUtil.getAuthentication();
    return userRepository.findByEmail(authentication.getName()).orElseThrow(() ->
        new UserException(ErrorCode.NOT_FOUND_USER));
  }
}
