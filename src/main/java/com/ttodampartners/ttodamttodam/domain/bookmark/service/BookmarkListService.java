package com.ttodampartners.ttodamttodam.domain.bookmark.service;

import com.ttodampartners.ttodamttodam.domain.bookmark.dto.BookmarkDto;
import com.ttodampartners.ttodamttodam.domain.bookmark.entity.BookmarkEntity;
import com.ttodampartners.ttodamttodam.domain.bookmark.repository.BookmarkRepository;
import com.ttodampartners.ttodamttodam.global.util.UserUtil;
import jakarta.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BookmarkListService {

  private final BookmarkRepository bookmarkRepository;
  private final UserUtil userUtil;

  @Transactional
  public List<BookmarkDto> getBookmarkList() {
    List<BookmarkEntity> bookmarkList =
        bookmarkRepository.findByUserId(userUtil.getCurUserEntity().getId());

    return bookmarkList.stream()
        .map(BookmarkDto::of)
        .collect(Collectors.toList());
  }
}
