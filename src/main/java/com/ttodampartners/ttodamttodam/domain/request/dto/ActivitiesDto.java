package com.ttodampartners.ttodamttodam.domain.request.dto;

import com.ttodampartners.ttodamttodam.domain.post.entity.PostEntity;
import com.ttodampartners.ttodamttodam.domain.request.entity.RequestEntity;
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
    private List<String> members;
    private Long postId;
    private String title;
    private List<String> productName;
    private PostEntity.Status status;
    private PostEntity.PurchaseStatus purchaseStatus;
    private RequestEntity.RequestStatus requestStatus;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public static ActivitiesDto of(RequestEntity request) {
        List<String> productNames = request.getPost().getProducts().stream()
                .map(productEntity -> productEntity.getProductName())
                .collect(Collectors.toList());

        String title = request.getPost().getTitle();
        Long postId = request.getPost().getPostId();
        LocalDateTime createdAt = request.getPost().getCreatedAt();
        LocalDateTime updatedAt = request.getPost().getUpdatedAt();
        PostEntity.Status status = request.getPost().getStatus();
        PostEntity.PurchaseStatus purchaseStatus = request.getPost().getPurchaseStatus();
        RequestEntity.RequestStatus requestStatus = request.getRequestStatus();

        List<String> members = List.of(request.getRequestUser().getNickname());

        return ActivitiesDto.builder()
                .postId(postId)
                .members(members)
                .title(title)
                .productName(productNames)
                .createdAt(createdAt)
                .updatedAt(updatedAt)
                .status(status)
                .requestStatus(requestStatus)
                .purchaseStatus(purchaseStatus)
                .build();
    }
}

