package com.ttodampartners.ttodamttodam.domain.participation.service;

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
public class ParticipationCancelService {

  private final UserUtil userUtil;
  private final ParticipationRepository participationRepository;

  @Transactional
  public void cancelParticipationRequest(Long requestId) {
    UserEntity curUser = userUtil.getCurUserEntity();
    ParticipationEntity request = getRequest(requestId);

    Long requestUserId = request.getRequestUser().getId();

    if (!curUser.getId().equals(requestUserId)) {
      throw new ParticipationException(ErrorCode.REQUEST_PERMISSION_DENIED);
    }

    participationRepository.delete(request);
  }

  private ParticipationEntity getRequest(Long requestId) {
    return participationRepository.findById(requestId).orElseThrow(() ->
        new ParticipationException(ErrorCode.NOT_FOUND_REQUEST));
  }
}
