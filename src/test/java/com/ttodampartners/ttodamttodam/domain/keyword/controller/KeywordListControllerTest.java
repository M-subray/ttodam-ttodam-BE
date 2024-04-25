package com.ttodampartners.ttodamttodam.domain.keyword.controller;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

import com.ttodampartners.ttodamttodam.domain.keyword.dto.response.KeywordCheckResponseDto;
import com.ttodampartners.ttodamttodam.domain.keyword.service.KeywordListService;
import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

@ExtendWith(MockitoExtension.class)
@DisplayName("키워드 조회 컨트롤러 테스트")
class KeywordListControllerTest {
  @Mock
  private KeywordListService keywordListService;

  @InjectMocks
  private KeywordListController keywordListController;

  @Test
  public void checkKeyword_ReturnsKeywordList_Success() {
    // given
    KeywordCheckResponseDto keyword1 =
        new KeywordCheckResponseDto(1L, 1L, "키워드1");
    KeywordCheckResponseDto keyword2 =
        new KeywordCheckResponseDto(2L, 2L, "키워드2");
    List<KeywordCheckResponseDto> expectedKeywords = Arrays.asList(keyword1, keyword2);

    // when
    when(keywordListService.getKeywordList()).thenReturn(expectedKeywords);
    ResponseEntity<List<KeywordCheckResponseDto>> responseEntity =
        keywordListController.getKeywordList();

    // then
    assertEquals(expectedKeywords, responseEntity.getBody());
    assertEquals(200, responseEntity.getStatusCodeValue());
  }
}