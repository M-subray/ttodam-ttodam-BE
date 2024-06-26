package com.ttodampartners.ttodamttodam.domain.keyword.controller;

import com.ttodampartners.ttodamttodam.domain.keyword.dto.request.KeywordCreateRequestDto;
import com.ttodampartners.ttodamttodam.domain.keyword.dto.response.KeywordCreateResponseDto;
import com.ttodampartners.ttodamttodam.domain.keyword.service.KeywordCreateService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Slf4j
public class KeywordCreateController {
  private final KeywordCreateService keywordCreateService;

  @PostMapping("users/keywords")
  public ResponseEntity<?> createKeyword (
      @ModelAttribute @Valid KeywordCreateRequestDto keywordName) {
    KeywordCreateResponseDto responseDto = keywordCreateService.createKeyword(keywordName);

    return ResponseEntity.ok(responseDto);
  }
}
