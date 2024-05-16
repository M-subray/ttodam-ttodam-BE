package com.ttodampartners.ttodamttodam.domain.bookmark.service;

import com.ttodampartners.ttodamttodam.domain.bookmark.dto.BookmarkDto;
import com.ttodampartners.ttodamttodam.domain.bookmark.entity.BookmarkEntity;
import com.ttodampartners.ttodamttodam.domain.bookmark.exception.BookmarkException;
import com.ttodampartners.ttodamttodam.domain.bookmark.repository.BookmarkRepository;
import com.ttodampartners.ttodamttodam.domain.post.entity.PostEntity;
import com.ttodampartners.ttodamttodam.domain.post.util.PostUtil;
import com.ttodampartners.ttodamttodam.domain.user.entity.UserEntity;
import com.ttodampartners.ttodamttodam.global.util.UserUtil;
import com.ttodampartners.ttodamttodam.global.error.ErrorCode;
import jakarta.transaction.Transactional;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class BookmarkRegisterService {

  private final UserUtil userUtil;
  private final PostUtil postUtil;
  private final BookmarkRepository bookmarkRepository;

  @Transactional
  public BookmarkDto registerBookmark(Long postId) {
    UserEntity user = userUtil.getCurUserEntity();
    PostEntity post = postUtil.getPost(postId);

    alreadyRegisterCheck(user, post);

    BookmarkEntity bookmarkEntity =
        bookmarkRepository.save(BookmarkEntity.builder()
            .user(user)
            .post(post)
            .build());

    return BookmarkDto.of(bookmarkEntity);
  }

  private void alreadyRegisterCheck (UserEntity curUser, PostEntity newPost) {
    Long newPostId = newPost.getPostId();

    List<BookmarkEntity> bookmarkEntities =
        bookmarkRepository.findByUserId(curUser.getId());
    for (BookmarkEntity bookmark : bookmarkEntities) {
      if (bookmark.getPost().getPostId().equals(newPostId)) {
        throw new BookmarkException(ErrorCode.ALREADY_REGISTER_BOOKMARK);
      }
    }
  }
}
