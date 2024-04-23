package com.ttodampartners.ttodamttodam.domain.user.controller;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

import com.ttodampartners.ttodamttodam.domain.user.service.SocialService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@ExtendWith(MockitoExtension.class)
@DisplayName("소셜 로그인 테스트")
class SocialControllerTest {

  @Mock
  private SocialService socialService;

  @InjectMocks
  private SocialController socialController;

  @Test
  @DisplayName("로그인 성공 확인")
  void socialLogin_Success() {
    //given
    String REGISTRATION_ID = "testId";
    String CODE = "testCode";
    String TOKEN = "testToken";

    //when
    when(socialService.socialLogin(CODE, REGISTRATION_ID)).thenReturn(TOKEN);
    ResponseEntity<?> responseEntity = socialController.socialLogin(CODE, REGISTRATION_ID);

    //then
    assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    assertEquals("Bearer " + TOKEN,
        responseEntity.getHeaders().getFirst(HttpHeaders.AUTHORIZATION));
    assertEquals("로그인 성공", responseEntity.getBody());
  }
}