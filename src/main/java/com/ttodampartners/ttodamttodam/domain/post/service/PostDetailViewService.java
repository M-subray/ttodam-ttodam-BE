package com.ttodampartners.ttodamttodam.domain.post.service;

import com.ttodampartners.ttodamttodam.domain.bookmark.entity.BookmarkEntity;
import com.ttodampartners.ttodamttodam.domain.bookmark.repository.BookmarkRepository;
import com.ttodampartners.ttodamttodam.domain.post.dto.PostDetailDto;
import com.ttodampartners.ttodamttodam.domain.post.entity.PostEntity;
import com.ttodampartners.ttodamttodam.domain.post.exception.PostException;
import com.ttodampartners.ttodamttodam.domain.post.repository.PostRepository;
import com.ttodampartners.ttodamttodam.domain.request.entity.RequestEntity;
import com.ttodampartners.ttodamttodam.domain.request.repository.RequestRepository;
import com.ttodampartners.ttodamttodam.domain.user.entity.UserEntity;
import com.ttodampartners.ttodamttodam.global.util.UserUtil;
import com.ttodampartners.ttodamttodam.global.error.ErrorCode;
import jakarta.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PostDetailViewService {

  private final UserUtil userUtil;
  private final PostRepository postRepository;
  private final BookmarkRepository bookmarkRepository;
  private final RequestRepository requestRepository;

  @Transactional
  public PostDetailDto getPostDetail(Long postId) {
    UserEntity user = userUtil.getCurUserEntity();
    Long userId = user.getId();
    String userRoadName = roadName(user.getLocation());

    PostEntity post = postRepository.findById(postId)
        .orElseThrow(() -> new PostException(ErrorCode.NOT_FOUND_POST));

    Long bookmarkId = 0L;

    // 북마크 확인
    Optional<BookmarkEntity> bookmarkOptional = bookmarkRepository.findByPost_PostIdAndUserId(
        postId, userId);
    if (bookmarkOptional.isPresent()) {
      // 북마크가 존재하면 북마크 ID를 받아옴
      bookmarkId = bookmarkOptional.get().getBookmarkId();
    }

    String postRoadName = roadName(post.getPlace());

    // 로그인 유저 거주지와 만남장소 비교
    if (!userRoadName.equals(postRoadName)) {
      throw new PostException(ErrorCode.POST_READ_PERMISSION_DENIED);
    }

    // 작성자인지 판별
    boolean isAuthor = post.getUser().getId().equals(userId);

    List<RequestEntity> requestList = requestRepository.findAllByPost_PostId(postId);

    String loginUserRequestStatus = isAuthor ? "AUTHOR" : "NONE";

    if (!isAuthor) {
      // 요청자인지 확인 및 요청 상태 반환
      if (requestList != null && !requestList.isEmpty()) {
        for (RequestEntity request : requestList) {
          if (request.getRequestUser().getId().equals(userId)) {
            // 요청자인 경우 상태 반환
            if (request.getRequestStatus() == RequestEntity.RequestStatus.ACCEPT) {
              loginUserRequestStatus = "ACCEPT";
            } else if (request.getRequestStatus() == RequestEntity.RequestStatus.REFUSE) {
              loginUserRequestStatus = "REFUSE";
            } else {
              loginUserRequestStatus = "WAIT";
            }
            break;
          }
        }
      }
    }

    return PostDetailDto.of(post, requestList, loginUserRequestStatus, bookmarkId);
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
