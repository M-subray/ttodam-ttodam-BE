package com.ttodampartners.ttodamttodam.domain.bookmark.service;

import com.ttodampartners.ttodamttodam.domain.bookmark.dto.BookmarkDto;
import com.ttodampartners.ttodamttodam.domain.bookmark.entity.BookmarkEntity;
import com.ttodampartners.ttodamttodam.domain.bookmark.exception.BookmarkException;
import com.ttodampartners.ttodamttodam.domain.bookmark.repository.BookmarkRepository;
import com.ttodampartners.ttodamttodam.domain.post.entity.PostEntity;
import com.ttodampartners.ttodamttodam.domain.post.exception.PostException;
import com.ttodampartners.ttodamttodam.domain.post.repository.PostRepository;
import com.ttodampartners.ttodamttodam.domain.user.entity.UserEntity;
import com.ttodampartners.ttodamttodam.domain.user.exception.UserException;
import com.ttodampartners.ttodamttodam.domain.user.repository.UserRepository;
import com.ttodampartners.ttodamttodam.domain.user.util.AuthenticationUtil;
import com.ttodampartners.ttodamttodam.global.error.ErrorCode;
import jakarta.transaction.Transactional;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class BookmarkRegisterService {

  private final PostRepository postRepository;
  private final UserRepository userRepository;
  private final BookmarkRepository bookmarkRepository;

  @Transactional
  public BookmarkDto registerBookmark(Long postId) {
    UserEntity user = getUser();
    PostEntity post = getPost(postId);

    alreadyRegisterCheck(user, post);

    BookmarkEntity bookmarkEntity =
        bookmarkRepository.save(BookmarkEntity.builder()
            .user(user)
            .post(post)
            .build());

    return BookmarkDto.of(bookmarkEntity);
  }

  private UserEntity getUser() {
    Authentication authentication = AuthenticationUtil.getAuthentication();

    return userRepository.findByEmail(authentication.getName()).orElseThrow(() ->
        new UserException(ErrorCode.NOT_FOUND_USER));
  }

  private PostEntity getPost(Long postId) {
    return postRepository.findById(postId).orElseThrow(() ->
        new PostException(ErrorCode.NOT_FOUND_POST));
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
