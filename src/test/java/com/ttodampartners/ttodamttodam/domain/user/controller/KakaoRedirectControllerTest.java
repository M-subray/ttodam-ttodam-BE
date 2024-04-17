package com.ttodampartners.ttodamttodam.domain.user.controller;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.util.ReflectionTestUtils;

@ExtendWith(MockitoExtension.class)
@DisplayName("카카오 Redirect 테스트")
class KakaoRedirectControllerTest {

  @InjectMocks
  private KakaoRedirectController kakaoRedirectController;

  @Test
  @DisplayName("카카오 로그인 페이지 이동")
  void testKakaoRedirect () {
    //given
    String clientId = "9470e45f503c50ff5572f3c87a54e011";
    String redirectUrl = "http://localhost:8080/login/oauth2/code/kakao";
    String expectedAuthUrl =
        String.format("https://kauth.kakao.com/oauth/authorize?client_id=%s&redirect_uri=%s&response_type=code",
            clientId, redirectUrl);

    ReflectionTestUtils.setField(kakaoRedirectController, "clientId", clientId);
    ReflectionTestUtils.setField(kakaoRedirectController, "redirectUrl", redirectUrl);

    //when
    ResponseEntity<?> responseEntity = kakaoRedirectController.kakaoSignin();

    //then
    assertEquals(HttpStatus.FOUND, responseEntity.getStatusCode());
    assertEquals(expectedAuthUrl, responseEntity.getHeaders().getLocation().toString());
  }
}