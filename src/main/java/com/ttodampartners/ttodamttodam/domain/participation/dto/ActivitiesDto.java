package com.ttodampartners.ttodamttodam.domain.participation.dto;

import com.ttodampartners.ttodamttodam.domain.participation.type.ParticipationStatus;
import com.ttodampartners.ttodamttodam.domain.post.dto.ProductListDto;
import com.ttodampartners.ttodamttodam.domain.post.entity.PostEntity;
import com.ttodampartners.ttodamttodam.domain.participation.entity.ParticipationEntity;
import lombok.*;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ActivitiesDto {
    // 현재 로그인 유저 정보
    private Long userId;
    private String userNickname;
    private ParticipationStatus participationStatus;
    //참여 게시글 정보
    private Long postId;
    private Long authorId;
    private String authorNickname;
    private String title;
    private PostEntity.Status status;
    private PostEntity.PurchaseStatus purchaseStatus;
    private List<ProductListDto> products;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public static ActivitiesDto of(ParticipationEntity request) {

        List<ProductListDto> products = request.getPost().getProducts()
                .stream().map(ProductListDto::from).collect(Collectors.toList());

        return ActivitiesDto.builder()
                .userId(request.getRequestUser().getId())
                .userNickname(request.getRequestUser().getNickname())
                .postId(request.getPost().getPostId())
                .authorId(request.getPost().getUser().getId())
                .authorNickname(request.getPost().getUser().getNickname())
                .title(request.getPost().getTitle())
                .status(request.getPost().getStatus())
                .participationStatus(request.getParticipationStatus())
                .purchaseStatus(request.getPost().getPurchaseStatus())
                .products(products)
                .createdAt(request.getPost().getCreatedAt())
                .updatedAt(request.getPost().getUpdatedAt())
                .build();
    }
}


