package com.ttodampartners.ttodamttodam.domain.post.controller;

import com.ttodampartners.ttodamttodam.domain.post.dto.PostCreateDto;
import com.ttodampartners.ttodamttodam.domain.post.dto.PostDto;
import com.ttodampartners.ttodamttodam.domain.post.service.PostWriteService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@Slf4j
@RequiredArgsConstructor
public class PostWriteController {

  private final PostWriteService postWriteService;

  @PostMapping("/post/write")
  public ResponseEntity<PostDto> createPost(
      @RequestPart(value = "imageFiles", required = false) List<MultipartFile> imageFiles,
      @RequestPart PostCreateDto postCreateDto) {

    return ResponseEntity.ok(postWriteService.createPost(imageFiles, postCreateDto));
  }
}
