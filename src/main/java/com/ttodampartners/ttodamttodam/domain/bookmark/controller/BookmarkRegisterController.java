package com.ttodampartners.ttodamttodam.domain.bookmark.controller;

import com.ttodampartners.ttodamttodam.domain.bookmark.dto.BookmarkDto;
import com.ttodampartners.ttodamttodam.domain.bookmark.service.BookmarkRegisterService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@Slf4j
@RestController
public class BookmarkRegisterController {

  private final BookmarkRegisterService bookmarkRegisterService;

  @PostMapping("/post/{postId}/bookmark")
  public ResponseEntity<BookmarkDto> registerBookmark(@PathVariable Long postId) {
    BookmarkDto bookmarkDto = bookmarkRegisterService.registerBookmark(postId);

    log.info(String.format("userId : [%d] 이 postId : [%d] 에 대한 북마크 등록 성공",
        bookmarkDto.getUserId(), bookmarkDto.getPostId()));
    return ResponseEntity.ok(bookmarkDto);
  }
}