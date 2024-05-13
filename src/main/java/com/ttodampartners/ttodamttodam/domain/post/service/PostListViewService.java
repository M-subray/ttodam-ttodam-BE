package com.ttodampartners.ttodamttodam.domain.post.service;

import com.ttodampartners.ttodamttodam.domain.post.dto.PostListDto;
import com.ttodampartners.ttodamttodam.domain.post.entity.PostEntity;
import com.ttodampartners.ttodamttodam.domain.post.repository.PostRepository;
import com.ttodampartners.ttodamttodam.domain.user.entity.UserEntity;
import com.ttodampartners.ttodamttodam.global.util.UserUtil;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PostListViewService {

  private final PostRepository postRepository;
  private final UserUtil userUtil;

  /*
  전체 글 조회
  로그인된 유저의 도로명 주소(-로)를 기준으로 게시글의 만남장소를 특정하여 게시글 목록 불러오기
   */
  @Transactional(readOnly = true)
  public List<PostListDto> getPostList() {
    UserEntity user = userUtil.getCurUserEntity();
    String userRoadName = roadName(user.getLocation());
    List<PostEntity> postList = postRepository.findAll();

    // 유저와 동일한 도로명을 가진 게시글 필터링
    List<PostEntity> filteredPosts = postList.stream()
        .filter(post -> {
          String postRoadName = roadName(post.getPlace());
          return postRoadName.equals(userRoadName);
        })
        .collect(Collectors.toList());

    return filteredPosts.stream()
        .map(PostListDto::of)
        .collect(Collectors.toList());
  }

  // 카테고리별 글 조회
  @Transactional(readOnly = true)
  public List<PostListDto> getCategoryPostList(String category) {
    UserEntity user = userUtil.getCurUserEntity();
    String userRoadName = roadName(user.getLocation());

    PostEntity.Category currentCategory = PostEntity.Category.fromLabel(category);
    List<PostEntity> postList = postRepository.findByCategory(currentCategory);

    // 유저와 동일한 도로명을 가진 게시글 필터링
    List<PostEntity> filteredPosts = postList.stream()
        .filter(post -> {
          String postRoadName = roadName(post.getPlace());
          return postRoadName.equals(userRoadName);
        })
        .collect(Collectors.toList());

    return filteredPosts.stream()
        .map(PostListDto::of)
        .collect(Collectors.toList());
  }

  // 내가 작성한 글 조회
  @Transactional(readOnly = true)
  public List<PostListDto> getUsersPostList() {
    UserEntity user = userUtil.getCurUserEntity();
    List<PostEntity> usersPostList = postRepository.findByUserId(user.getId());

    return usersPostList.stream()
        .map(PostListDto::of)
        .collect(Collectors.toList());
  }

  // 키워드로 글 조회
  @Transactional(readOnly = true)
  public List<PostListDto> searchPostList(String word) {
    List<PostEntity> searchPostList = postRepository.findBySearch(word);

    return searchPostList.stream()
        .map(PostListDto::of)
        .collect(Collectors.toList());
  }

  // 도로명 주소에서 -로 부분 추출
  private String roadName(String address) {
    Pattern pattern = Pattern.compile("(\\S+로)");
    Matcher matcher = pattern.matcher(address);

    if (matcher.find()) {
      return matcher.group();
    } else {
      return "";
    }
  }
}
