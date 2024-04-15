package com.ttodampartners.ttodamttodam.domain.user.controller;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.util.ReflectionTestUtils;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
@DisplayName("구글 Redirect 테스트")
public class GoogleRedirectControllerTest {

  @InjectMocks
  private GoogleRedirectController googleRedirectController;

  @Test
  @DisplayName("구글 로그인 페이지 이동")
  void testGoogleRedirect () {
    //given
    String clientId =
        "903701232606-a5sul2dd17bongihm4reqfsfnrl5g8uo.apps.googleusercontent.com";
    String redirectUrl =
        "http://localhost:8080/login/oauth2/code/google";
    String scope =
        "https://www.googleapis.com/auth/userinfo.email%20https://www.googleapis.com/auth/userinfo.profile";

    String expectedAuthUrl = String.format(
        "https://accounts.google.com/o/oauth2/auth?client_id=%s&redirect_uri=%s&response_type=code&scope=%s",
        clientId, redirectUrl, scope
    );

    ReflectionTestUtils.setField(googleRedirectController, "clientId", clientId);
    ReflectionTestUtils.setField(googleRedirectController, "redirectUrl", redirectUrl);

    //when
    ResponseEntity<?> responseEntity = googleRedirectController.googleSignin();

    //then
    assertEquals(HttpStatus.FOUND, responseEntity.getStatusCode());
    assertEquals(expectedAuthUrl, responseEntity.getHeaders().getLocation().toString());
  }
}
