package com.ttodampartners.ttodamttodam.domain.post.controller;

import com.ttodampartners.ttodamttodam.domain.post.dto.PostListDto;
import com.ttodampartners.ttodamttodam.domain.post.service.PostListViewService;
import com.ttodampartners.ttodamttodam.global.dto.UserDetailsDto;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@RequiredArgsConstructor
public class PostListViewController {

  private final PostListViewService postListViewService;

  // 전체 글 조회
  @GetMapping("/post/list")
  public ResponseEntity<List<PostListDto>> getPostList() {
    List<PostListDto> postList = postListViewService.getPostList();

    return ResponseEntity.ok(postList);
  }

  // 카테고리별 글 조회
  @GetMapping("/post/category/{category}")
  public ResponseEntity<List<PostListDto>> getCategoryPostList(
      @PathVariable String category) {
    List<PostListDto> postList = postListViewService.getCategoryPostList(category);

    return ResponseEntity.ok(postList);
  }

  // 내가 작성한 글 조회
  @GetMapping("/users/post/list")
  public ResponseEntity<List<PostListDto>> getUsersPostList(
      @AuthenticationPrincipal UserDetailsDto userDetails) {
    List<PostListDto> userPostList = postListViewService.getUsersPostList();

    return ResponseEntity.ok(userPostList);
  }

  // 키워드로 글 조회
  @GetMapping("/post/search")
  public ResponseEntity<List<PostListDto>> searchPostList(
      @RequestParam(required = false) String word) {
    List<PostListDto> searchPostList = postListViewService.searchPostList(word);

    return ResponseEntity.ok(searchPostList);
  }
}
