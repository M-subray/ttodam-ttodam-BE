package com.ttodampartners.ttodamttodam.domain.post.controller;

import com.ttodampartners.ttodamttodam.domain.post.dto.PostDto;
import com.ttodampartners.ttodamttodam.domain.post.dto.PostUpdateDto;
import com.ttodampartners.ttodamttodam.domain.post.service.PostUpdateService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@Slf4j
@RequiredArgsConstructor
public class PostUpdateController {

  private final PostUpdateService postUpdateService;

  @PutMapping("/post/{postId}")
  public ResponseEntity<PostDto> updatePost(
      @PathVariable Long postId,
      @RequestPart PostUpdateDto postUpdateDto,
      @RequestPart(value = "imageFiles", required = false) List<MultipartFile> newImageFiles) {

    return ResponseEntity.ok(postUpdateService.updatePost(postId, postUpdateDto, newImageFiles));
  }

  @PutMapping("/post/{postId}/purchase/{purchaseStatus}")
  public ResponseEntity<PostDto> updatePurchaseStatus(
      @PathVariable Long postId, @PathVariable String purchaseStatus) {

    return ResponseEntity.ok(postUpdateService.updatePurchaseStatus(postId, purchaseStatus));
  }
}
