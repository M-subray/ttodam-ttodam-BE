package com.ttodampartners.ttodamttodam.domain.participation.entity;

import com.ttodampartners.ttodamttodam.domain.participation.type.ParticipationStatus;
import com.ttodampartners.ttodamttodam.domain.post.entity.PostEntity;
import com.ttodampartners.ttodamttodam.domain.user.entity.UserEntity;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EnableJpaAuditing
@Builder
@EntityListeners(AuditingEntityListener.class)
@Entity(name = "request")
public class ParticipationEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id", nullable = false)
  private Long requestId;

  @ManyToOne
  @JoinColumn(name = "request_user", nullable = false)
  private UserEntity requestUser;

  @JoinColumn(name = "request_member_manner_evaluated")
  private boolean requestMemberMannerEvaluated;

  @ManyToOne
  @JoinColumn(name = "post_id", nullable = false)
  private PostEntity post;

  @Column(name = "request_status", nullable = false)
  @Enumerated(EnumType.STRING)
  private ParticipationStatus participationStatus;

  @Column(name = "create_at", nullable = false)
  @CreatedDate
  private LocalDateTime createdAt;

  @Column(name = "update_at", nullable = false)
  @LastModifiedDate
  private LocalDateTime updatedAt;
}