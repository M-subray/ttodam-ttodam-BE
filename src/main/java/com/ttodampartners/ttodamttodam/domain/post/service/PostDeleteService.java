package com.ttodampartners.ttodamttodam.domain.post.service;

import com.amazonaws.services.s3.AmazonS3;
import com.ttodampartners.ttodamttodam.domain.post.entity.PostEntity;
import com.ttodampartners.ttodamttodam.domain.post.exception.PostException;
import com.ttodampartners.ttodamttodam.domain.post.repository.PostRepository;
import com.ttodampartners.ttodamttodam.domain.post.util.PostUtil;
import com.ttodampartners.ttodamttodam.domain.user.entity.UserEntity;
import com.ttodampartners.ttodamttodam.global.util.UserUtil;
import com.ttodampartners.ttodamttodam.global.error.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class PostDeleteService {

  private final PostRepository postRepository;
  private final UserUtil userUtil;
  private final PostUtil postUtil;
  private final AmazonS3 amazonS3;

  @Value("${spring.cloud.aws.s3.bucket}")
  private String bucket;


  @Transactional
  public void deletePost(Long postId) {
    UserEntity user = userUtil.getCurUserEntity();
    PostEntity post = postUtil.getPost(postId);

    validateAuthority(user.getId(), post.getUser().getId());

    // 게시글 이미지 S3에서 삭제
    for (String deleteImageUrl : post.getImgUrls()) {
      deleteImageFileFromS3(deleteImageUrl);
    }

    postRepository.delete(post);
  }

  private void validateAuthority(Long userId, Long postAuthorId) {
    if (!userId.equals(postAuthorId)) {
      throw new PostException(ErrorCode.POST_PERMISSION_DENIED);
    }
  }

  private void deleteImageFileFromS3(String postImgUrl) {
    if (postImgUrl != null) {
      String postImg = getImageFileNameFromUrl(postImgUrl);
      amazonS3.deleteObject(bucket, postImg);
    }
  }

  private String getImageFileNameFromUrl(String imageUrl) {
    // URL 에서 파일 이름 추출 (예: https://example.com/images/profile.jpg -> profile.jpg)
    String[] parts = imageUrl.split("/");
    return parts[parts.length - 1];
  }
}
