package com.ttodampartners.ttodamttodam.domain.participation.service;

import com.ttodampartners.ttodamttodam.domain.chat.dto.event.GroupChatCreateEvent;
import com.ttodampartners.ttodamttodam.domain.participation.dto.ParticipationDto;
import com.ttodampartners.ttodamttodam.domain.participation.type.ParticipationStatus;
import com.ttodampartners.ttodamttodam.domain.post.entity.PostEntity;
import com.ttodampartners.ttodamttodam.domain.post.exception.PostException;
import com.ttodampartners.ttodamttodam.domain.post.repository.PostRepository;
import com.ttodampartners.ttodamttodam.domain.post.util.PostUtil;
import com.ttodampartners.ttodamttodam.domain.participation.entity.ParticipationEntity;
import com.ttodampartners.ttodamttodam.domain.participation.exception.ParticipationException;
import com.ttodampartners.ttodamttodam.domain.participation.repository.ParticipationRepository;
import com.ttodampartners.ttodamttodam.domain.user.entity.UserEntity;
import com.ttodampartners.ttodamttodam.global.error.ErrorCode;
import com.ttodampartners.ttodamttodam.global.util.UserUtil;
import jakarta.transaction.Transactional;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ParticipationUpdateService {

  private final UserUtil userUtil;
  private final PostUtil postUtil;
  private final ParticipationRepository participationRepository;
  // 단체 채팅방 생성을 위한 event publisher
  private final ApplicationEventPublisher eventPublisher;
  private final PostRepository postRepository;

  @Transactional
  public ParticipationDto updateRequestStatus(Long requestId, String requestStatus) {
    UserEntity curUser = userUtil.getCurUserEntity();
    ParticipationEntity participation = getRequest(requestId);
    PostEntity post = participation.getPost();

    // 주최자 인증
    validateAuthority(curUser.getId(), post);

    switch (post.getStatus()) {
      case COMPLETED -> throw new ParticipationException(ErrorCode.POST_STATUS_COMPLETED);
      case FAILED -> throw new ParticipationException(ErrorCode.POST_STATUS_FAILED);
    }

    if (requestStatus.equals((ParticipationStatus.ACCEPT).toString())) {
      System.out.println("수락일 때만 들어오는지 체크해보자");
      updatePostStatus(post.getPostId());
    }

    participation.setParticipationStatus(ParticipationStatus.fromLabel(requestStatus));
    participationRepository.save(participation);

    return ParticipationDto.of(participation);
  }

  @Transactional
  public void updatePostStatus(Long postId) {
    PostEntity post = postUtil.getPost(postId);

    // 특정 게시글의 참여 목록 조회
    List<ParticipationEntity> participation = participationRepository.findAllByPost_PostId(postId);

    // 수락된 요청 계산
    long acceptedParticipationCount = participation.stream()
        .filter(p -> p.getParticipationStatus() == ParticipationStatus.ACCEPT)
        .count();

    // 게시글의 희망 인원과 비교, 모집상태 변경
    if (acceptedParticipationCount == post.getParticipants()) {
      post.setStatus(PostEntity.Status.COMPLETED);
      postRepository.save(post);

      // 남은 모든 요청들의 상태를 거절로 변경
      for (ParticipationEntity request : participation) {
        if (request.getParticipationStatus() == ParticipationStatus.WAIT) {
          request.setParticipationStatus(ParticipationStatus.REFUSE);
          participationRepository.save(request);
        }
      }

      /*
      단체 채팅방 생성을 위한 event publish
       */
      eventPublisher.publishEvent(
          GroupChatCreateEvent.builder()
              .post(post)
              .requestEntities(participation.stream()
                  .filter(request -> request.getParticipationStatus() == ParticipationStatus.ACCEPT)
                  .toList()).build()
      );
    }
  }

  private ParticipationEntity getRequest(Long requestId) {
    return participationRepository.findById(requestId).orElseThrow(() ->
        new ParticipationException(ErrorCode.NOT_FOUND_REQUEST));
  }

  private void validateAuthority(Long userId, PostEntity post) {
    Long postAuthorId = post.getUser().getId();

    if (!userId.equals(postAuthorId)) {
      throw new PostException(ErrorCode.POST_PERMISSION_DENIED);
    }
  }
}
