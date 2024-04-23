package com.ttodampartners.ttodamttodam.domain.keyword.service;

import com.ttodampartners.ttodamttodam.domain.keyword.entity.KeywordEntity;
import com.ttodampartners.ttodamttodam.domain.keyword.exception.KeywordException;
import com.ttodampartners.ttodamttodam.domain.keyword.repository.KeywordRepository;
import com.ttodampartners.ttodamttodam.global.error.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class KeywordDeleteService {
  private final KeywordRepository keywordRepository;

  @Transactional
  public void keywordDelete (Long keywordId) {
    keywordRepository.delete(getKeywordEntity(keywordId));
  }

  private KeywordEntity getKeywordEntity (Long keywordId) {
    return  keywordRepository.findById(keywordId).orElseThrow(()
        -> new KeywordException(ErrorCode.NOT_FOUND_KEYWORD));
  }
}
