package com.ttodampartners.ttodamttodam.domain.user.dto;

import java.util.List;
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
public class MannersEvaluateCheckDto {
  List<Long> userIdList;
  List<String> userNicknameList;
}