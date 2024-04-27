package com.ttodampartners.ttodamttodam.domain.notification.dto;

import com.ttodampartners.ttodamttodam.domain.notification.entity.NotificationEntity.Type;
import com.ttodampartners.ttodamttodam.domain.user.dto.model.NotificationBaseEntity;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class NotificationViewDto {
  private Long notificationId;

  private Long userId;

  private Type type;

  private Long postId;

  private LocalDateTime createAt;
}
