package com.ttodampartners.ttodamttodam.domain.keyword.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
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
public class KeywordCreateRequestDto {
  @NotBlank(message = "등록하려는 키워드를 입력해 주세요")
  @Size(min = 1, max = 15, message = "키워드는 최소 한 글자, 최대 열다섯 글자 사이어야 합니다.")
  private String keywordName;
}
