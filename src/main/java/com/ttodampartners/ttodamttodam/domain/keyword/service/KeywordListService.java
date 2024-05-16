package com.ttodampartners.ttodamttodam.domain.keyword.service;

import com.ttodampartners.ttodamttodam.domain.keyword.dto.response.KeywordCheckResponseDto;
import com.ttodampartners.ttodamttodam.domain.keyword.entity.KeywordEntity;
import com.ttodampartners.ttodamttodam.domain.keyword.repository.KeywordRepository;
import com.ttodampartners.ttodamttodam.domain.user.entity.UserEntity;
import com.ttodampartners.ttodamttodam.domain.user.exception.UserException;
import com.ttodampartners.ttodamttodam.domain.user.repository.UserRepository;
import com.ttodampartners.ttodamttodam.global.util.UserUtil;
import com.ttodampartners.ttodamttodam.global.error.ErrorCode;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class KeywordListService {
  private final KeywordRepository keywordRepository;
  private final UserRepository userRepository;

  @Transactional(readOnly = true)
  public List<KeywordCheckResponseDto> getKeywordList() {
    UserEntity user = getUser();

    List<KeywordEntity> keywordEntityList =
        keywordRepository.findAllByUserId(user.getId());

    return keywordEntityList.stream()
        .map(this::mapToResponseForKeywordDto)
        .collect(Collectors.toList());
  }

  private KeywordCheckResponseDto mapToResponseForKeywordDto(KeywordEntity keywordEntity) {
    return KeywordCheckResponseDto.builder()
        .id(keywordEntity.getId())
        .keywordName(keywordEntity.getKeywordName())
        .userId(keywordEntity.getUser().getId())
        .build();
  }

  private UserEntity getUser () {
    Authentication authentication = UserUtil.getAuthentication();
    return userRepository.findByEmail(authentication.getName()).orElseThrow(() ->
        new UserException(ErrorCode.NOT_FOUND_USER));
  }
}
