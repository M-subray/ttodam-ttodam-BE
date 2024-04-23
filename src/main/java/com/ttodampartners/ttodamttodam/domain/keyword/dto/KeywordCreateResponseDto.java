package com.ttodampartners.ttodamttodam.domain.keyword.dto;

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
public class KeywordCreateResponseDto {
  private Long id;
  private String keywordName;
}
