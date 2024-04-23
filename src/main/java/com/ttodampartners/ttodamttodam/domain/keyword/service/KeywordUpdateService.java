package com.ttodampartners.ttodamttodam.domain.keyword.service;

import com.ttodampartners.ttodamttodam.domain.keyword.dto.request.KeywordUpdateRequestDto;
import com.ttodampartners.ttodamttodam.domain.keyword.dto.response.KeywordCreateResponseDto;
import com.ttodampartners.ttodamttodam.domain.keyword.entity.KeywordEntity;
import com.ttodampartners.ttodamttodam.domain.keyword.exception.KeywordException;
import com.ttodampartners.ttodamttodam.domain.keyword.repository.KeywordRepository;
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
public class KeywordUpdateService {
  private final KeywordRepository keywordRepository;
  private final UserRepository userRepository;

  public KeywordCreateResponseDto keywordUpdate(
      KeywordUpdateRequestDto keywordUpdateRequestDto) {
    // 로그인된 계정의 UserEntity 가져오기
    UserEntity user = getUser();
    // keywordId로 이미 등록된 키워드인지 확인
    existsKeyword(keywordUpdateRequestDto.getKeywordName(), user.getId());

    KeywordEntity keyword = getKeywordEntity(keywordUpdateRequestDto.getKeywordId());
    keyword.setKeywordName(keywordUpdateRequestDto.getKeywordName());

    return saveKeyword(keyword);
  }

  private KeywordCreateResponseDto saveKeyword (KeywordEntity keywordEntity) {
    keywordEntity = keywordRepository.save(keywordEntity);

    return KeywordCreateResponseDto.builder()
        .id(keywordEntity.getId())
        .keywordName(keywordEntity.getKeywordName())
        .build();
  }

  private KeywordEntity getKeywordEntity(Long id) {
    return keywordRepository.findById(id).orElseThrow(() ->
        new KeywordException(ErrorCode.NOT_FOUND_KEYWORD));
  }

  private UserEntity getUser () {
    Authentication authentication = AuthenticationUtil.getAuthentication();
    return userRepository.findByEmail(authentication.getName()).orElseThrow(() ->
        new UserException(ErrorCode.NOT_FOUND_USER));
  }

  private void existsKeyword (String keywordName, Long userId) {
    if (keywordRepository.existsByKeywordNameAndUserId(keywordName, userId)) {
      throw new KeywordException(ErrorCode.ALREADY_EXISTS_KEYWORD);
    }
  }
}
