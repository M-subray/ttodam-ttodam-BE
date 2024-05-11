package com.ttodampartners.ttodamttodam.domain.bookmark.controller;

import com.ttodampartners.ttodamttodam.domain.bookmark.dto.BookmarkDto;
import com.ttodampartners.ttodamttodam.domain.bookmark.service.BookmarkListService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@RequiredArgsConstructor
public class BookmarkListController {

  private final BookmarkListService bookmarkListService;

  @GetMapping("/post/bookmark")
  public ResponseEntity<List<BookmarkDto>> getBookmarkList() {
    List<BookmarkDto> bookmarkList = bookmarkListService.getBookmarkList();

    log.info("북마크 조회 성공");
    return ResponseEntity.ok(bookmarkList);
  }
}
