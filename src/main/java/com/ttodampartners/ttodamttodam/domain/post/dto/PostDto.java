package com.ttodampartners.ttodamttodam.domain.post.dto;

import com.ttodampartners.ttodamttodam.domain.post.entity.PostEntity;
import com.ttodampartners.ttodamttodam.domain.product.dto.ProductDto;
import com.ttodampartners.ttodamttodam.domain.user.entity.UserEntity;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PostDto {
    private Long postId;
    private UserEntity user;
    private List<ProductDto> products;
    private String title;
    private Integer participants;
    private String place;
    private Double pLocationX;
    private Double pLocationY;
    private LocalDateTime deadline;
    private PostEntity.Category category;
    private String content;
    private String postImgUrl;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;


    public static PostDto of(PostEntity postEntity) {
        List<ProductDto> products = postEntity.getProducts()
                .stream().map(ProductDto::from).collect(Collectors.toList());
        return PostDto.builder()
                .postId(postEntity.getPostId())
                .user(postEntity.getUser())
                .title(postEntity.getTitle())
                .participants(postEntity.getParticipants())
                .place(postEntity.getPlace())
                .pLocationX(postEntity.getPLocationX())
                .pLocationY(postEntity.getPLocationY())
                .deadline(postEntity.getDeadline())
                .category(postEntity.getCategory())
                .content(postEntity.getContent())
                .postImgUrl(postEntity.getPostImgUrl())
                .createdAt(postEntity.getCreatedAt())
                .updatedAt(postEntity.getUpdatedAt())
                .products(products)
                .build();
    }

}