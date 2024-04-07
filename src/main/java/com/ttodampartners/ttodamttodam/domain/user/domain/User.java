package com.ttodampartners.ttodamttodam.domain.user.domain;

import com.ttodampartners.ttodamttodam.domain.user.dto.model.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.envers.AuditOverride;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@AuditOverride(forClass = BaseEntity.class)
@Entity
public class User extends BaseEntity {

  @Id
  @Column(name = "id", nullable = false)
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(unique = true, nullable = false)
  private String email;

  @Column(nullable = false)
  private String password;

  @Column(unique = true, nullable = false)
  private String phone;

  @Column(nullable = false)
  private String location;

  @Column(unique = true, nullable = false)
  @Size(min = 3, max = 8)
  private String nickname;

  @Column(nullable = true)
  private String profileImgUrl;

  @Column(name = "location_x", nullable = false)
  private double locationX;

  @Column(name = "location_y", nullable = false)
  private double locationY;

  @Column(nullable = false, columnDefinition = "DOUBLE DEFAULT 0")
  private double manners;
}
