package com.ttodampartners.ttodamttodam.domain.post.dto;

import com.ttodampartners.ttodamttodam.domain.post.entity.PostEntity;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PostDto {
    private Long postId;
    //    private UserEntity userId;
    private String title;
    private Integer participants;
    private String place;
    private Double pLocationX;
    private Double pLocationY;
    private LocalDateTime deadline;
    private PostEntity.Category category;
    private String content;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    private String productName;
    private String purchaseLink;
    private Long price;
    private String productImgUrl;

    public static PostDto of(PostEntity postEntity) {
        return PostDto.builder()
                .postId(postEntity.getPostId())
                .title(postEntity.getTitle())
                .participants(postEntity.getParticipants())
                .place(postEntity.getPlace())
                .pLocationX(postEntity.getPLocationX())
                .pLocationY(postEntity.getPLocationY())
                .deadline(postEntity.getDeadline())
                .category(postEntity.getCategory())
                .content(postEntity.getContent())
                .createdAt(postEntity.getCreatedAt())
                .updatedAt(postEntity.getUpdatedAt())

                .productName(postEntity.getProductName())
                .purchaseLink(postEntity.getPurchaseLink())
                .price(postEntity.getPrice())
                .productImgUrl(postEntity.getProductImgUrl())
                .build();
    }

}