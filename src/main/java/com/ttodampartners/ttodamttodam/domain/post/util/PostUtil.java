package com.ttodampartners.ttodamttodam.domain.post.util;

import com.ttodampartners.ttodamttodam.domain.post.entity.PostEntity;
import com.ttodampartners.ttodamttodam.domain.post.exception.PostException;
import com.ttodampartners.ttodamttodam.domain.post.repository.PostRepository;
import com.ttodampartners.ttodamttodam.global.error.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PostUtil {

  private final PostRepository postRepository;

  public PostEntity getPost(Long postId) {
    return postRepository.findById(postId).orElseThrow(() ->
        new PostException(ErrorCode.NOT_FOUND_POST));
  }
}
