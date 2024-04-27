package com.ttodampartners.ttodamttodam.domain.user.service;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.ttodampartners.ttodamttodam.domain.user.entity.UserEntity;
import com.ttodampartners.ttodamttodam.domain.user.exception.UserException;
import com.ttodampartners.ttodamttodam.domain.user.repository.UserRepository;
import com.ttodampartners.ttodamttodam.domain.user.util.AuthenticationUtil;
import com.ttodampartners.ttodamttodam.global.error.ErrorCode;
import com.ttodampartners.ttodamttodam.domain.user.exception.AwsException;
import java.io.IOException;
import java.util.UUID;
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
public class ImageUpdateService {
  private final AmazonS3 amazonS3;
  private final UserRepository userRepository;

  @Value("${spring.cloud.aws.s3.bucket}")
  private String bucket;

  @Transactional
  public void imageUpdate(MultipartFile newImage) {
    UserEntity user = getUser();
    isMatchEmail(user);

    String oldImage = user.getProfileImgUrl();  // 기존 image 빼오기
    user.setProfileImgUrl(saveFile(newImage));  // 새 이미지 s3 및 UserEntity 에 url 저장
    deleteOldProfileImage(oldImage);            // 기존 image s3에서 삭제
  }

  private String saveFile(MultipartFile newImage) {
    try {
      String uploadImageName = UUID.randomUUID().toString();

      ObjectMetadata metadata = new ObjectMetadata();
      metadata.setContentLength(newImage.getSize());
      metadata.setContentType(newImage.getContentType());

      amazonS3.putObject(bucket, uploadImageName, newImage.getInputStream(), metadata);

      return amazonS3.getUrl(bucket, uploadImageName).toString();
    } catch (IOException e) {
      throw new AwsException(ErrorCode.UPLOAD_FAILED);
    }
  }

  private void deleteOldProfileImage(String oldImage) {
    // 프로필에 사진이 존재했다면 S3에서 삭제
    if (oldImage != null) {
      oldImage = getImageFileNameFromUrl(oldImage);
      amazonS3.deleteObject(bucket, oldImage);
    }
  }

  private String getImageFileNameFromUrl (String imageUrl) {
    // URL 에서 파일 이름 추출 (예: https://example.com/images/profile.jpg -> profile.jpg)
    String[] parts = imageUrl.split("/");
    return parts[parts.length - 1];
  }

  private UserEntity getUser () {
    Authentication authentication = AuthenticationUtil.getAuthentication();
    return userRepository.findByEmail(authentication.getName()).orElseThrow(() ->
        new UserException(ErrorCode.NOT_FOUND_USER));
  }

  private void isMatchEmail(UserEntity user) {
    Authentication authentication = AuthenticationUtil.getAuthentication();
    String authEmail = authentication.getName();

    if (!authEmail.equals(user.getEmail())) {
      throw new UserException(ErrorCode.PERMISSION_DENIED);
    }
  }
}
