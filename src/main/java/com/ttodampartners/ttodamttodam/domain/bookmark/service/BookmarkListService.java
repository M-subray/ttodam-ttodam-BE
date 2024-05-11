package com.ttodampartners.ttodamttodam.domain.bookmark.service;

import com.ttodampartners.ttodamttodam.domain.bookmark.dto.BookmarkDto;
import com.ttodampartners.ttodamttodam.domain.bookmark.entity.BookmarkEntity;
import com.ttodampartners.ttodamttodam.domain.bookmark.repository.BookmarkRepository;
import com.ttodampartners.ttodamttodam.domain.user.entity.UserEntity;
import com.ttodampartners.ttodamttodam.domain.user.exception.UserException;
import com.ttodampartners.ttodamttodam.domain.user.repository.UserRepository;
import com.ttodampartners.ttodamttodam.domain.user.util.AuthenticationUtil;
import com.ttodampartners.ttodamttodam.global.error.ErrorCode;
import jakarta.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BookmarkListService {

  private final BookmarkRepository bookmarkRepository;
  private final UserRepository userRepository;

  @Transactional
  public List<BookmarkDto> getBookmarkList() {
    List<BookmarkEntity> bookmarkList = findBookmarkForCurUser();

    return bookmarkList.stream()
        .map(BookmarkDto::of)
        .collect(Collectors.toList());
  }

  private List<BookmarkEntity> findBookmarkForCurUser() {
    Authentication authentication = AuthenticationUtil.getAuthentication();
    UserEntity curUser = userRepository.findByEmail(authentication.getName()).orElseThrow(() ->
        new UserException(ErrorCode.NOT_FOUND_USER));

    return bookmarkRepository.findByUserId(curUser.getId());
  }
}
