package com.ttodampartners.ttodamttodam.domain.keyword.service;

import com.ttodampartners.ttodamttodam.domain.keyword.dto.request.KeywordCreateRequestDto;
import com.ttodampartners.ttodamttodam.domain.keyword.dto.request.KeywordUpdateRequestDto;
import com.ttodampartners.ttodamttodam.domain.keyword.dto.response.KeywordCreateResponseDto;
import com.ttodampartners.ttodamttodam.domain.keyword.entity.KeywordEntity;
import com.ttodampartners.ttodamttodam.domain.keyword.exception.KeywordException;
import com.ttodampartners.ttodamttodam.domain.keyword.repository.KeywordRepository;
import com.ttodampartners.ttodamttodam.global.error.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class KeywordUpdateService {
  private final KeywordRepository keywordRepository;

  public KeywordCreateResponseDto keywordUpdate(
      KeywordUpdateRequestDto keywordUpdateRequestDto) {

    KeywordEntity keyword = getKeywordEntity(keywordUpdateRequestDto.getId());
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
}
