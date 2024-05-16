package com.ttodampartners.ttodamttodam.domain.participation.dto;

import com.ttodampartners.ttodamttodam.domain.participation.entity.ParticipationEntity;
import com.ttodampartners.ttodamttodam.domain.participation.type.ParticipationStatus;
import lombok.*;
import java.time.LocalDateTime;


@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ParticipationDto {

  private Long requestId;
  private Long requestUserId;
  private String requestUserNickname;
  private ParticipationStatus participationStatus;
  private LocalDateTime createdAt;
  private LocalDateTime updatedAt;

  public static ParticipationDto of(ParticipationEntity participationEntity) {
    return ParticipationDto.builder()
        .requestId(participationEntity.getRequestId())
        .requestUserNickname(participationEntity.getRequestUser().getNickname())
        .requestUserId(participationEntity.getRequestUser().getId())
        .participationStatus(participationEntity.getParticipationStatus())
        .createdAt(participationEntity.getCreatedAt())
        .updatedAt(participationEntity.getUpdatedAt())
        .build();
  }
}

