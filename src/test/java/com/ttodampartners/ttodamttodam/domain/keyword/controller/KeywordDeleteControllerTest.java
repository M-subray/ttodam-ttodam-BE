package com.ttodampartners.ttodamttodam.domain.keyword.controller;

import static org.junit.jupiter.api.Assertions.*;

import com.ttodampartners.ttodamttodam.domain.keyword.service.KeywordDeleteService;
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
@DisplayName("키워드 삭제 컨트롤러 테스트")
class KeywordDeleteControllerTest {
  @Mock
  private KeywordDeleteService keywordDeleteService;

  @InjectMocks
  private KeywordDeleteController keywordDeleteController;

  @Test
  void keywordDeleteTest() {
    //given
    Long keywordId = 1L;

    //when
    Mockito.doNothing().when(keywordDeleteService).keywordDelete(keywordId);
    ResponseEntity<String> response = keywordDeleteController.keywordDelete(keywordId);

    //then
    assertEquals(response.getStatusCode(), HttpStatus.OK);
    assertEquals(response.getBody(), "키워드가 삭제되었습니다.");
  }


}