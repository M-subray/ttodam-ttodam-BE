package com.ttodampartners.ttodamttodam.domain.post.controller;

import static org.springframework.http.HttpStatus.OK;

import com.ttodampartners.ttodamttodam.domain.post.service.PostDeleteService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@RequiredArgsConstructor
public class PostDeleteController {
  private final PostDeleteService postDeleteService;

  @DeleteMapping("/post/{postId}")
  public ResponseEntity<Void> deletePost(@PathVariable Long postId) {
    postDeleteService.deletePost(postId);

    return ResponseEntity.status(OK).build();
  }
}
