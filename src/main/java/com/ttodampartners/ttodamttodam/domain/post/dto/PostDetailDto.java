package com.ttodampartners.ttodamttodam.domain.post.dto;

import com.ttodampartners.ttodamttodam.domain.post.entity.PostEntity;
import com.ttodampartners.ttodamttodam.domain.request.dto.RequestDto;
import com.ttodampartners.ttodamttodam.domain.request.entity.RequestEntity;
import lombok.*;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PostDetailDto {
    private PostListDto post;
    private List<RequestDto> requestList;
    private String loginUserRequestStatus;
    private boolean isBookmarked;

    public static PostDetailDto of(PostEntity postEntity, List<RequestEntity> requestEntities, String loginUserRequestStatus, boolean isBookmarked) {
        return PostDetailDto.builder()
                .post(PostListDto.of(postEntity))
                .requestList(requestEntities.stream()
                        .map(RequestDto::of)
                        .collect(Collectors.toList()))
                .loginUserRequestStatus(loginUserRequestStatus)
                .isBookmarked(isBookmarked)
                .build();
    }
}
