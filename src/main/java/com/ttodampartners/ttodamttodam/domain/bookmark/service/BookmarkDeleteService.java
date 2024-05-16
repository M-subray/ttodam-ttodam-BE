package com.ttodampartners.ttodamttodam.domain.bookmark.service;

import com.ttodampartners.ttodamttodam.domain.bookmark.entity.BookmarkEntity;
import com.ttodampartners.ttodamttodam.domain.bookmark.exception.BookmarkException;
import com.ttodampartners.ttodamttodam.domain.bookmark.repository.BookmarkRepository;
import com.ttodampartners.ttodamttodam.domain.user.entity.UserEntity;
import com.ttodampartners.ttodamttodam.global.util.UserUtil;
import com.ttodampartners.ttodamttodam.global.error.ErrorCode;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BookmarkDeleteService {

  private final BookmarkRepository bookmarkRepository;
  private final UserUtil userUtil;

  @Transactional
  public void deleteBookmark(Long bookmarkId) {
    UserEntity curUser = userUtil.getCurUserEntity();
    BookmarkEntity bookmark = getBookmark(bookmarkId);

    // 권한 인증
    Long curUserId = curUser.getId();
    Long bookmarkUserId = bookmark.getUser().getId();
    if (!curUserId.equals(bookmarkUserId)) {
      throw new BookmarkException(ErrorCode.BOOKMARK_PERMISSION_DENIED);
    }

    bookmarkRepository.delete(bookmark);
  }

  private BookmarkEntity getBookmark(Long bookmarkId) {

    return bookmarkRepository.findById(bookmarkId)
        .orElseThrow(() -> new BookmarkException(ErrorCode.NOT_FOUND_BOOKMARK));
  }
}
