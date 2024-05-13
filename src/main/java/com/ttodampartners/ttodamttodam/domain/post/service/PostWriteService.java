package com.ttodampartners.ttodamttodam.domain.post.service;

import com.ttodampartners.ttodamttodam.domain.notification.service.NotificationService;
import com.ttodampartners.ttodamttodam.domain.post.dto.PostCreateDto;
import com.ttodampartners.ttodamttodam.domain.post.dto.PostDto;
import com.ttodampartners.ttodamttodam.domain.post.entity.PostEntity;
import com.ttodampartners.ttodamttodam.domain.post.repository.PostRepository;
import com.ttodampartners.ttodamttodam.domain.user.entity.UserEntity;
import com.ttodampartners.ttodamttodam.global.util.UserUtil;
import com.ttodampartners.ttodamttodam.global.util.CoordinateFinderUtil;
import com.ttodampartners.ttodamttodam.global.util.ImageSaveToS3Util;
import jakarta.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
public class PostWriteService {

  private final PostRepository postRepository;
  private final CoordinateFinderUtil coordinateFinderUtil;
  private final NotificationService notificationService;
  private final UserUtil userUtil;
  private final ImageSaveToS3Util imageSaveToS3Util;

  @Transactional
  public PostDto createPost(List<MultipartFile> imageFiles, PostCreateDto postCreateDto) {
    UserEntity user = userUtil.getCurUserEntity();

    // S3에 저장된 이미지 url
    List<String> postImgUrls = uploadImageFilesToS3(imageFiles);
    PostEntity post = PostCreateDto.of(user, postImgUrls, postCreateDto);
    // 저장된 만남장소 주소정보로 위도,경도 저장
    double[] coordinates = coordinateFinderUtil.getCoordinates(postCreateDto.getPlace());
    post.setPLocationX(coordinates[1]); // 경도 설정
    post.setPLocationY(coordinates[0]); // 위도 설정

    postRepository.save(post);
    // 키워드(프로덕트 이름 리스트)로 알림 발송
    notificationService.sendNotificationForKeyword(postCreateDto, post);

    return PostDto.of(post);
  }

  private List<String> uploadImageFilesToS3(List<MultipartFile> imageFiles) {
    List<String> imageUrls = new ArrayList<>();

    for (MultipartFile imageFile : imageFiles) {
      imageUrls.add(imageSaveToS3Util.uploadImageFile(imageFile));
    }

    return imageUrls;
  }
}
