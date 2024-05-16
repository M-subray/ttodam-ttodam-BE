package com.ttodampartners.ttodamttodam.domain.post.dto;

import com.ttodampartners.ttodamttodam.domain.post.entity.PostEntity;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PostUpdateDto {

  private String title;

  private Integer participants;

  private String place;

  private LocalDateTime deadline;

  private PostEntity.Category category;

  private String content;

  private List<String> imgUrls;

  private List<ProductUpdateDto> products;
}
