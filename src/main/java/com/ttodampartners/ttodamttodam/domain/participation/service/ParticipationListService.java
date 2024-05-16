package com.ttodampartners.ttodamttodam.domain.participation.service;

import com.ttodampartners.ttodamttodam.domain.post.entity.PostEntity;
import com.ttodampartners.ttodamttodam.domain.post.exception.PostException;
import com.ttodampartners.ttodamttodam.domain.post.repository.PostRepository;
import com.ttodampartners.ttodamttodam.domain.participation.dto.ActivitiesDto;
import com.ttodampartners.ttodamttodam.domain.participation.dto.ParticipationDto;
import com.ttodampartners.ttodamttodam.domain.participation.entity.ParticipationEntity;
import com.ttodampartners.ttodamttodam.domain.participation.repository.ParticipationRepository;
import com.ttodampartners.ttodamttodam.domain.post.util.PostUtil;
import com.ttodampartners.ttodamttodam.domain.user.entity.UserEntity;
import com.ttodampartners.ttodamttodam.global.error.ErrorCode;
import com.ttodampartners.ttodamttodam.global.util.UserUtil;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class ParticipationListService {

  private final ParticipationRepository participationRepository;
  private final UserUtil userUtil;
  private final PostUtil postUtil;


  @Transactional
  public List<ActivitiesDto> getParticipationListForCurUser() {
    UserEntity curUser = userUtil.getCurUserEntity();

    // 로그인 유저가 참여요청 내역
    List<ParticipationEntity> usersActivities =
        participationRepository.findAllByRequestUser_Id(curUser.getId());

    return usersActivities.stream()
        .map(ActivitiesDto::of)
        .collect(Collectors.toList());
  }

  @Transactional
  public List<ParticipationDto> getParticipationListFromPost(Long postId) {
    UserEntity curUser = userUtil.getCurUserEntity();
    List<ParticipationEntity> requestList = participationRepository.findAllByPost_PostId(postId);

    PostEntity post = postUtil.getPost(postId);

    // 주최자 인증
    validateAuthority(curUser.getId(), post);

    return requestList.stream()
        .map(ParticipationDto::of)
        .collect(Collectors.toList());
  }

  private void validateAuthority(Long userId, PostEntity post) {
    Long postAuthorId = post.getUser().getId();

    if (!userId.equals(postAuthorId)) {
      throw new PostException(ErrorCode.POST_PERMISSION_DENIED);
    }
  }
}
