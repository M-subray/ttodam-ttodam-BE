package com.ttodampartners.ttodamttodam.domain.user.dto;

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
public class ProfileViewDto {
  private String nickname;
  private String profileImgUrl;
  private double manners;
}
