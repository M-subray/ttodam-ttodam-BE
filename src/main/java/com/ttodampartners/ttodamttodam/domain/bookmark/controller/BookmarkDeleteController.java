package com.ttodampartners.ttodamttodam.domain.bookmark.controller;

import static org.springframework.http.HttpStatus.OK;

import com.ttodampartners.ttodamttodam.domain.bookmark.service.BookmarkDeleteService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@RequiredArgsConstructor
public class BookmarkDeleteController {

  private final BookmarkDeleteService bookmarkDeleteService;

  @DeleteMapping("/post/bookmark/{bookmarkId}")
  public ResponseEntity<Void> deleteBookmark(@PathVariable Long bookmarkId) {
    bookmarkDeleteService.deleteBookmark(bookmarkId);

    log.info("북마크 삭제 완료");
    return ResponseEntity.status(OK).build();
  }
}
