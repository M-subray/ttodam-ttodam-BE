package com.ttodampartners.ttodamttodam.global.util;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.ttodampartners.ttodamttodam.domain.user.exception.AwsException;
import com.ttodampartners.ttodamttodam.global.error.ErrorCode;
import java.io.IOException;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

@Component
@RequiredArgsConstructor
public class ImageSaveToS3Util {

  private final AmazonS3 amazonS3;
  @Value("${spring.cloud.aws.s3.bucket}")
  private String bucket;

  public String uploadImageFile(MultipartFile image) {
    try {
      String uploadImageName = UUID.randomUUID().toString();

      ObjectMetadata metadata = new ObjectMetadata();
      metadata.setContentLength(image.getSize());
      metadata.setContentType(image.getContentType());

      amazonS3.putObject(bucket, uploadImageName, image.getInputStream(), metadata);

      return amazonS3.getUrl(bucket, uploadImageName).toString();
    } catch (IOException e) {
      throw new AwsException(ErrorCode.UPLOAD_FAILED);
    }
  }
}
