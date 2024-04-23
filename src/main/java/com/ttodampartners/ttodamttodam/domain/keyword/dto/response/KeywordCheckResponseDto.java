package com.ttodampartners.ttodamttodam.domain.keyword.dto.response;

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
public class KeywordCheckResponseDto {
  private Long id;
  private Long userId;
  private String keywordName;
}
