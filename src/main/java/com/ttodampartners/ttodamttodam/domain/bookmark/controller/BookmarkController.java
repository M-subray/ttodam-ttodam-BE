package com.ttodampartners.ttodamttodam.domain.bookmark.controller;

import com.ttodampartners.ttodamttodam.domain.bookmark.dto.BookmarkDto;
import com.ttodampartners.ttodamttodam.domain.bookmark.service.BookmarkService;
import com.ttodampartners.ttodamttodam.domain.post.dto.PostDto;
import com.ttodampartners.ttodamttodam.global.dto.UserDetailsDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RequiredArgsConstructor
@RestController
public class BookmarkController {
    private final BookmarkService bookmarkService;

    @PostMapping("/post/{postId}/bookmark")
    public ResponseEntity<BookmarkDto> createBookmark(
            @AuthenticationPrincipal UserDetailsDto userDetails,
            @PathVariable Long postId
        ) {
        Long userId = userDetails.getId();
        return ResponseEntity.ok(BookmarkDto.of(bookmarkService.createBookmark(userId, postId)));
       }


    @GetMapping("/post/bookmark")
    public ResponseEntity<List<BookmarkDto>> getBookmarkList(
            @AuthenticationPrincipal UserDetailsDto userDetails
    ){
        Long userId = userDetails.getId();
        List<BookmarkDto> bookmarkList = bookmarkService.getBookmarkList(userId);
        return ResponseEntity.ok(bookmarkList);
    }
}
