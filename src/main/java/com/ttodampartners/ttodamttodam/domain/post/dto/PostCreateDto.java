package com.ttodampartners.ttodamttodam.domain.post.dto;

import com.ttodampartners.ttodamttodam.domain.post.entity.PostEntity;
import com.ttodampartners.ttodamttodam.domain.post.entity.ProductEntity;
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

    private List<ProductAddDto> products;


    public static PostEntity of(UserEntity user, List<String> postImgUrls, PostCreateDto postCreateDto) {

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
                .postImgUrls(postImgUrls)
                .build();

        List<ProductEntity> productEntities = postCreateDto.getProducts().stream()
                .map(ProductAddDto::from)
                .peek(productEntity -> productEntity.setPost(postEntity))
                .collect(Collectors.toList());

        postEntity.setProducts(productEntities);
        return postEntity;
    }
}

