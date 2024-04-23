package com.ttodampartners.ttodamttodam.domain.user.controller;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;

import com.ttodampartners.ttodamttodam.domain.user.service.ImageUpdateService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

@ExtendWith(MockitoExtension.class)
@DisplayName("이미지 업데이트 컨트롤러 테스트")
class ImageUpdateControllerTest {

  @Mock
  ImageUpdateService imageUpdateService;

  @InjectMocks
  ImageUpdateController imageUpdateController;

  @Test
  void imageUpdateTest() {
    //given
    Long userId = 1L;
    MultipartFile file = new MockMultipartFile("file", "test.jpg",
        "image/jpeg", new byte[0]);

    //when
    doNothing().when(imageUpdateService).imageUpdate(any(Long.class), any(MultipartFile.class));
    ResponseEntity<String> response = imageUpdateController.imageUpdate(userId, file);

    //then
    assertEquals(HttpStatus.OK, response.getStatusCode());
    assertEquals("정상적으로 수정되었습니다.", response.getBody());
  }
}