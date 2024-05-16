package com.ttodampartners.ttodamttodam.domain.participation.service;

import com.ttodampartners.ttodamttodam.domain.participation.type.ParticipationStatus;
import com.ttodampartners.ttodamttodam.domain.post.entity.PostEntity;
import com.ttodampartners.ttodamttodam.domain.post.util.PostUtil;
import com.ttodampartners.ttodamttodam.domain.participation.dto.ParticipationDto;
import com.ttodampartners.ttodamttodam.domain.participation.entity.ParticipationEntity;
import com.ttodampartners.ttodamttodam.domain.participation.exception.ParticipationException;
import com.ttodampartners.ttodamttodam.domain.participation.repository.ParticipationRepository;
import com.ttodampartners.ttodamttodam.domain.user.entity.UserEntity;
import com.ttodampartners.ttodamttodam.global.error.ErrorCode;
import com.ttodampartners.ttodamttodam.global.util.UserUtil;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ParticipationRequestService {

  private final UserUtil userUtil;
  private final PostUtil postUtil;
  private final ParticipationRepository participationRepository;

  @Transactional
  public ParticipationDto sendRequest(Long postId) {
    UserEntity requestUser = userUtil.getCurUserEntity();
    PostEntity post = postUtil.getPost(postId);

    // 게시글의 상태에 따른 참여요청 응답
    switch (post.getStatus()) {
      case COMPLETED -> throw new ParticipationException(ErrorCode.POST_STATUS_COMPLETED);
      case FAILED -> throw new ParticipationException(ErrorCode.POST_STATUS_FAILED);
    }

    ParticipationEntity participationEntity = participationRepository.save(ParticipationEntity.builder()
        .requestUser(requestUser)
        .post(post)
        .participationStatus(ParticipationStatus.WAIT)
        .build());

    return ParticipationDto.of(participationEntity);
  }
}
