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
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
public class ImageUpdateService {
  private final AmazonS3 amazonS3;
  private final UserRepository userRepository;

  @Value("${spring.cloud.aws.s3.bucket}")
  private String bucket;

  @Transactional
  public void imageUpdate(Long userId, MultipartFile file) {
    UserEntity user = getUser(userId);
    isMatchEmail(user);

    user.setProfileImgUrl(saveFile(file));
  }
  private String saveFile(MultipartFile multipartFile) {
    try {
      String originalFilename = multipartFile.getOriginalFilename();

      ObjectMetadata metadata = new ObjectMetadata();
      metadata.setContentLength(multipartFile.getSize());
      metadata.setContentType(multipartFile.getContentType());

      amazonS3.putObject(bucket, originalFilename, multipartFile.getInputStream(), metadata);

      return amazonS3.getUrl(bucket, originalFilename).toString();
    } catch (IOException e) {
      throw new AwsException(ErrorCode.UPLOAD_FAILED);
    }
  }

  private UserEntity getUser(Long userId) {
    return userRepository.findById(userId).orElseThrow(() ->
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
