package com.ttodampartners.ttodamttodam.domain.post.service;

import com.amazonaws.services.s3.AmazonS3;
import com.ttodampartners.ttodamttodam.domain.post.dto.PostDto;
import com.ttodampartners.ttodamttodam.domain.post.dto.PostUpdateDto;
import com.ttodampartners.ttodamttodam.domain.post.dto.ProductUpdateDto;
import com.ttodampartners.ttodamttodam.domain.post.entity.PostEntity;
import com.ttodampartners.ttodamttodam.domain.post.entity.ProductEntity;
import com.ttodampartners.ttodamttodam.domain.post.exception.PostException;
import com.ttodampartners.ttodamttodam.domain.post.repository.PostRepository;
import com.ttodampartners.ttodamttodam.domain.post.repository.ProductRepository;
import com.ttodampartners.ttodamttodam.domain.post.util.PostUtil;
import com.ttodampartners.ttodamttodam.domain.request.exception.RequestException;
import com.ttodampartners.ttodamttodam.domain.user.entity.UserEntity;
import com.ttodampartners.ttodamttodam.global.util.UserUtil;
import com.ttodampartners.ttodamttodam.global.util.CoordinateFinderUtil;
import com.ttodampartners.ttodamttodam.global.error.ErrorCode;
import com.ttodampartners.ttodamttodam.global.util.ImageSaveToS3Util;
import jakarta.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
public class PostUpdateService {

  private final PostRepository postRepository;
  private final CoordinateFinderUtil coordinateFinderUtil;
  private final UserUtil userUtil;
  private final PostUtil postUtil;
  private final AmazonS3 amazonS3;
  private final ProductRepository productRepository;
  private final ImageSaveToS3Util imageSaveToS3Util;

  @Value("${spring.cloud.aws.s3.bucket}")
  private String bucket;

  @Transactional
  public PostDto updatePost(Long postId, PostUpdateDto postUpdateDto,
      List<MultipartFile> newImageFiles) {
    UserEntity user = userUtil.getCurUserEntity();
    PostEntity post = postUtil.getPost(postId);

    validateAuthority(user.getId(), post);

    // 새로운 이미지 업로드
    List<String> newImageUrls = new ArrayList<>();
    for (MultipartFile multipartFile : newImageFiles) {
      newImageUrls.add(imageSaveToS3Util.uploadImageFile(multipartFile));
    }

    // 이미지목록 업데이트
    List<String> allImageUrls = new ArrayList<>();
    if (postUpdateDto.getImgUrls() != null) {
      allImageUrls.addAll(postUpdateDto.getImgUrls());
    }

    allImageUrls.addAll(newImageUrls);
    updateProductsImages(post, allImageUrls);

    post.setTitle(postUpdateDto.getTitle());
    post.setParticipants(postUpdateDto.getParticipants());
    post.setDeadline(postUpdateDto.getDeadline());
    post.setCategory(postUpdateDto.getCategory());
    post.setContent(postUpdateDto.getContent());

    // 만남 장소 정보가 변경되었을 때 위도와 경도를 업데이트
    if (!post.getPlace().equals(postUpdateDto.getPlace())) {
      double[] coordinates = coordinateFinderUtil.getCoordinates(postUpdateDto.getPlace());
      post.setPLocationX(coordinates[1]); // 경도 설정
      post.setPLocationY(coordinates[0]); // 위도 설정
    }

    post.setPlace(postUpdateDto.getPlace());
    // 상품목록 업데이트
    updateProducts(post, postUpdateDto.getProducts());
    postRepository.save(post);

    return PostDto.of(post);
  }

  private void updateProductsImages(PostEntity post, List<String> allImageUrls) {
    for (String postImageUrl : post.getImgUrls()) {
      if (!allImageUrls.contains(postImageUrl)) {
        deleteImageFileFromS3(postImageUrl);
      }
    }
    post.setImgUrls(allImageUrls);
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

  private void updateProducts(PostEntity post, List<ProductUpdateDto> products) {
    if (post.getProducts() == null) {
      post.setProducts(new ArrayList<>());
    }

    List<ProductEntity> existingProducts = post.getProducts();
    for (ProductUpdateDto productUpdateDto : products) {
      ProductEntity product = existingProducts.stream()
          .filter(pi -> pi.getProductId().equals(productUpdateDto.getProductId()))
          .findFirst().orElseThrow(() -> new PostException(ErrorCode.NOT_FOUND_PRODUCT));

      product.setProductName(productUpdateDto.getProductName());
      product.setCount(productUpdateDto.getCount());
      product.setPurchaseLink(productUpdateDto.getPurchaseLink());
      product.setPrice(productUpdateDto.getPrice());

      productRepository.save(product);
    }
  }

  @Transactional
  public PostDto updatePurchaseStatus(Long postId, String purchaseStatus) {
    UserEntity user = userUtil.getCurUserEntity();
    PostEntity post = postUtil.getPost(postId);

    // 주최자 인증
    validateAuthority(user.getId(), post);

    if (post.getStatus() == PostEntity.Status.IN_PROGRESS) {
      throw new RequestException(ErrorCode.POST_STATUS_IN_PROGRESS);
    } else if (post.getStatus() == PostEntity.Status.FAILED) {
      throw new RequestException(ErrorCode.POST_STATUS_FAILED);
    }

    post.setPurchaseStatus(PostEntity.PurchaseStatus.fromLabel(purchaseStatus));
    postRepository.save(post);

    return PostDto.of(post);
  }


  private void validateAuthority(Long userId, PostEntity post) {
    if (!userId.equals(post.getUser().getId())) {
      throw new PostException(ErrorCode.POST_PERMISSION_DENIED);
    }
  }
}
