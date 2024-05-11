package com.ttodampartners.ttodamttodam.domain.bookmark.service;

import com.ttodampartners.ttodamttodam.domain.bookmark.entity.BookmarkEntity;
import com.ttodampartners.ttodamttodam.domain.bookmark.exception.BookmarkException;
import com.ttodampartners.ttodamttodam.domain.bookmark.repository.BookmarkRepository;
import com.ttodampartners.ttodamttodam.domain.user.entity.UserEntity;
import com.ttodampartners.ttodamttodam.domain.user.exception.UserException;
import com.ttodampartners.ttodamttodam.domain.user.repository.UserRepository;
import com.ttodampartners.ttodamttodam.domain.user.util.AuthenticationUtil;
import com.ttodampartners.ttodamttodam.global.error.ErrorCode;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BookmarkDeleteService {

  private final BookmarkRepository bookmarkRepository;
  private final UserRepository userRepository;

  @Transactional
  public void deleteBookmark(Long bookmarkId) {
    UserEntity curUser = getUser();
    BookmarkEntity bookmark = getBookmark(bookmarkId);

    // 권한 인증
    Long curUserId = curUser.getId();
    Long bookmarkUserId = bookmark.getUser().getId();
    if (!curUserId.equals(bookmarkUserId)) {
      throw new BookmarkException(ErrorCode.BOOKMARK_PERMISSION_DENIED);
    }

    bookmarkRepository.delete(bookmark);
  }

  private UserEntity getUser() {
    Authentication authentication = AuthenticationUtil.getAuthentication();

    return userRepository.findByEmail(authentication.getName()).orElseThrow(() ->
        new UserException(ErrorCode.NOT_FOUND_USER));
  }

  private BookmarkEntity getBookmark(Long bookmarkId) {

    return bookmarkRepository.findById(bookmarkId)
        .orElseThrow(() -> new BookmarkException(ErrorCode.NOT_FOUND_BOOKMARK));
  }
}
