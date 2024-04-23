package com.ttodampartners.ttodamttodam.domain.keyword.controller;

import static org.junit.jupiter.api.Assertions.*;

import com.ttodampartners.ttodamttodam.domain.keyword.dto.request.KeywordUpdateRequestDto;
import com.ttodampartners.ttodamttodam.domain.keyword.dto.response.KeywordCreateResponseDto;
import com.ttodampartners.ttodamttodam.domain.keyword.service.KeywordUpdateService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@ExtendWith(MockitoExtension.class)
@DisplayName("키워드 수정 컨트롤러 테스트")
class KeywordUpdateControllerTest {
  @Mock
  private KeywordUpdateService keywordUpdateService;

  @InjectMocks
  private KeywordUpdateController keywordUpdateController;

  @Test
  void keywordUpdateTest() {
    //given
    Long id = 1L;
    String keyword = "테스트";
    KeywordUpdateRequestDto requestDto = new KeywordUpdateRequestDto(id, keyword);
    KeywordCreateResponseDto responseDto = new KeywordCreateResponseDto(id, keyword);

    //when
    Mockito.when(keywordUpdateService.keywordUpdate(requestDto)).thenReturn(responseDto);
    ResponseEntity<?> response = keywordUpdateController.keywordUpdate(requestDto);

    //then
    assertEquals(response.getStatusCode(), HttpStatus.OK);
    assertEquals(response.getBody(), responseDto);
  }
}