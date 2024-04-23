package com.ttodampartners.ttodamttodam.domain.bookmark.service;

import com.ttodampartners.ttodamttodam.domain.bookmark.dto.BookmarkDto;
import com.ttodampartners.ttodamttodam.domain.bookmark.entity.BookmarkEntity;
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

}
