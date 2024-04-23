package com.ttodampartners.ttodamttodam.domain.keyword.controller;

import static org.junit.jupiter.api.Assertions.*;

import com.ttodampartners.ttodamttodam.domain.keyword.dto.KeywordCreateRequestDto;
import com.ttodampartners.ttodamttodam.domain.keyword.dto.KeywordCreateResponseDto;
import com.ttodampartners.ttodamttodam.domain.keyword.service.KeywordCreateService;
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
@DisplayName("키워드 등록 컨트롤러 테스트")
class KeywordCreateControllerTest {

  @Mock
  private KeywordCreateService keywordCreateService;

  @InjectMocks
  private KeywordCreateController keywordCreateController;

  @Test
  void keywordCreateTest() {
    //given
    String keyword = "테스트";
    KeywordCreateRequestDto requestDto = new KeywordCreateRequestDto(keyword);
    KeywordCreateResponseDto responseDto = new KeywordCreateResponseDto(1L, keyword);

    //when
    Mockito.when(keywordCreateService.createKeyword(requestDto)).thenReturn(responseDto);
    ResponseEntity<?> response = keywordCreateController.createKeyword(requestDto);

    //then
    assertEquals(response.getStatusCode(), HttpStatus.OK);
    assertEquals(response.getBody(), responseDto);
  }
}