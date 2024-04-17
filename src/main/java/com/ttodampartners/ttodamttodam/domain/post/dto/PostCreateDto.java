package com.ttodampartners.ttodamttodam.domain.post.dto;

import com.ttodampartners.ttodamttodam.domain.post.entity.PostEntity;
import com.ttodampartners.ttodamttodam.domain.product.dto.ProductAddDto;
import com.ttodampartners.ttodamttodam.domain.product.entity.ProductEntity;
import com.ttodampartners.ttodamttodam.domain.user.entity.UserEntity;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PostCreateDto {

    private Long postId;
    private UserEntity user;

    @NotBlank(message = "제목을 입력해 주세요!")
    private String title;

    @NotBlank(message = "희망 인원을 입력해 주세요!")
    private Integer participants;

    @NotBlank(message = "만남장소를 입력해 주세요!")
    private String place;

    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm")
    private LocalDateTime deadline;

    private PostEntity.Status status;
    private PostEntity.Category category;
    private String content;
    private String postImgUrl;

    private List<ProductAddDto> products;


    public static PostEntity of(UserEntity user, PostCreateDto postCreateDto) {

        List<ProductAddDto> products = postCreateDto.getProducts();
        if (products == null) {
            products = Collections.emptyList();
        }

        PostEntity postEntity = PostEntity.builder()
                .postId(postCreateDto.getPostId())
                .user(user)
                .title(postCreateDto.getTitle())
                .participants(postCreateDto.getParticipants())
                .place(postCreateDto.getPlace())
                .deadline(postCreateDto.getDeadline())
                .status(postCreateDto.getStatus())
                .category(postCreateDto.getCategory())
                .content(postCreateDto.getContent())
                .postImgUrl(postCreateDto.getPostImgUrl())
                .build();

        List<ProductEntity> productEntities = products.stream()
                .map(productAddDto -> {
                    ProductEntity productEntity = ProductAddDto.from(productAddDto);
                    productEntity.setPost(postEntity);
                    return productEntity;
                })
                .collect(Collectors.toList());

        postEntity.setProducts(productEntities);
        return postEntity;
    }
}

