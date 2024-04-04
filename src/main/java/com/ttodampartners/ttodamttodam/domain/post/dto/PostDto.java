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
    private String category;
    private String content;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    private String productName;
    private String purchaseLink;
    private Long price;
    private String productImgUrl;

}