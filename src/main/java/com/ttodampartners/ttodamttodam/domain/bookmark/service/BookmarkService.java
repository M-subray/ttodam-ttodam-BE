package com.ttodampartners.ttodamttodam.domain.bookmark.service;

import com.ttodampartners.ttodamttodam.domain.bookmark.dto.BookmarkDto;
import com.ttodampartners.ttodamttodam.domain.bookmark.entity.BookmarkEntity;
import com.ttodampartners.ttodamttodam.domain.bookmark.exception.BookmarkException;
import com.ttodampartners.ttodamttodam.domain.bookmark.repository.BookmarkRepository;
import com.ttodampartners.ttodamttodam.domain.post.dto.PostDto;
import com.ttodampartners.ttodamttodam.domain.post.entity.PostEntity;
import com.ttodampartners.ttodamttodam.domain.post.exception.PostException;
import com.ttodampartners.ttodamttodam.domain.post.repository.PostRepository;
import com.ttodampartners.ttodamttodam.domain.user.entity.UserEntity;
import com.ttodampartners.ttodamttodam.domain.user.exception.UserException;
import com.ttodampartners.ttodamttodam.domain.user.repository.UserRepository;
import com.ttodampartners.ttodamttodam.global.error.ErrorCode;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;


@RequiredArgsConstructor
@Service
public class BookmarkService {

    private final PostRepository postRepository;
    private final UserRepository userRepository;
    private final BookmarkRepository bookmarkRepository;

    @Transactional
    public BookmarkEntity createBookmark(Long userId, Long postId) {
        UserEntity user = userRepository.findById(userId)
                .orElseThrow(() -> new UserException(ErrorCode.NOT_FOUND_USER));

        PostEntity post = postRepository.findById(postId)
                .orElseThrow(() -> new PostException(ErrorCode.NOT_FOUND_POST));

        BookmarkEntity bookmark = BookmarkEntity.builder()
                .user(user)
                .post(post)
                .build();

        return bookmarkRepository.save(bookmark);
    }

    @Transactional
    public List<BookmarkDto> getBookmarkList(Long userId) {

        List<BookmarkEntity> bookmarkList = bookmarkRepository.findByUserId(userId);

        return bookmarkList.stream()
                .map(BookmarkDto::of)
                .collect(Collectors.toList());
    }

    @Transactional
    public void deleteBookmark(Long userId, Long bookmarkId) {
        BookmarkEntity bookmark = bookmarkRepository.findById(bookmarkId)
                .orElseThrow(() -> new BookmarkException(ErrorCode.NOT_FOUND_BOOKMARK));

        userRepository.findById(userId)
                .orElseThrow(() -> new UserException(ErrorCode.NOT_FOUND_USER));

        Long bookmarkUserId = bookmark.getUser().getId();
        // 권한 인증
        if (!userId.equals(bookmarkUserId)) {
            throw new BookmarkException(ErrorCode.BOOKMARK_PERMISSION_DENIED);
        }

        bookmarkRepository.delete(bookmark);
    }

    // 게시글 삭제 시 북마크도 함께 삭제
    @Transactional
    public void deleteBookmarksByPost(Long postId) {
        List<BookmarkEntity> bookmarks = bookmarkRepository.findAllByPost_PostId(postId);
        bookmarkRepository.deleteAll(bookmarks);
    }
    // 회원 탈퇴 시 북마크도 함께 삭제
    @Transactional
    public void deleteBookmarksByUser(Long userId) {
        List<BookmarkEntity> bookmarks = bookmarkRepository.findByUserId(userId);
        bookmarkRepository.deleteAll(bookmarks);
    }

}
