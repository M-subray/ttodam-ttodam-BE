package com.ttodampartners.ttodamttodam.domain.post.controller;

import static org.springframework.http.HttpStatus.OK;

import com.ttodampartners.ttodamttodam.domain.post.dto.PostDetailDto;
import com.ttodampartners.ttodamttodam.domain.post.service.PostDetailViewService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@RequiredArgsConstructor
public class PostDetailViewController {
  private final PostDetailViewService postDetailViewService;

  @GetMapping("/post/{postId}")
  public ResponseEntity<PostDetailDto> getPostDetail(@PathVariable Long postId) {
    PostDetailDto postDto = postDetailViewService.getPostDetail(postId);

    return ResponseEntity.status(OK).body(postDto);
  }

}
