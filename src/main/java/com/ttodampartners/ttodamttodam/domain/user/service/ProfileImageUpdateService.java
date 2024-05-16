package com.ttodampartners.ttodamttodam.domain.user.service;

import com.amazonaws.services.s3.AmazonS3;
import com.ttodampartners.ttodamttodam.domain.user.entity.UserEntity;
import com.ttodampartners.ttodamttodam.domain.user.exception.UserException;
import com.ttodampartners.ttodamttodam.domain.user.repository.UserRepository;
import com.ttodampartners.ttodamttodam.global.util.UserUtil;
import com.ttodampartners.ttodamttodam.global.error.ErrorCode;
import com.ttodampartners.ttodamttodam.global.util.ImageSaveToS3Util;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Service
@Slf4j
@RequiredArgsConstructor
public class ProfileImageUpdateService {
  private final AmazonS3 amazonS3;
  private final UserRepository userRepository;
  private final UserUtil userUtil;
  private final ImageSaveToS3Util imageSaveToS3Util;

  @Value("${spring.cloud.aws.s3.bucket}")
  private String bucket;

  @Transactional
  public void imageUpdate(MultipartFile newImage) {
    UserEntity user = userUtil.getCurUserEntity();
    isMatchEmail(user);

    String oldImageUrl = user.getProfileImgUrl();  // 기존 image 빼오기
    user.setProfileImgUrl(imageSaveToS3Util.uploadImageFile(newImage));  // 새 이미지 s3 및 UserEntity 에 url 저장
    deleteOldProfileImage(oldImageUrl);            // 기존 image s3에서 삭제
    userRepository.save(user);
  }

  private void deleteOldProfileImage(String oldImageUrl) {
    // 프로필에 사진이 존재했다면 S3에서 삭제
    if (oldImageUrl != null) {
      String oldImage = getImageFileNameFromUrl(oldImageUrl);
      amazonS3.deleteObject(bucket, oldImage);
    }
  }

  private String getImageFileNameFromUrl (String imageUrl) {
    // URL 에서 파일 이름 추출 (예: https://example.com/images/profile.jpg -> profile.jpg)
    String[] parts = imageUrl.split("/");
    return parts[parts.length - 1];
  }

  private void isMatchEmail(UserEntity user) {
    Authentication authentication = UserUtil.getAuthentication();
    String authEmail = authentication.getName();

    if (!authEmail.equals(user.getEmail())) {
      throw new UserException(ErrorCode.PERMISSION_DENIED);
    }
  }
}
