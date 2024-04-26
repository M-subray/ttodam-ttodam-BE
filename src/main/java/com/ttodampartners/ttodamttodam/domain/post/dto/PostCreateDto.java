package com.ttodampartners.ttodamttodam.domain.post.dto;

import com.ttodampartners.ttodamttodam.domain.post.entity.PostEntity;
import com.ttodampartners.ttodamttodam.domain.post.entity.ProductEntity;
import com.ttodampartners.ttodamttodam.domain.user.entity.UserEntity;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;
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

    @Builder.Default
    private PostEntity.Status status = PostEntity.Status.IN_PROGRESS;

    @Builder.Default
    private PostEntity.PurchaseStatus purchaseStatus = PostEntity.PurchaseStatus.PREPARING;

    private PostEntity.Category category;

    private String content;

    private List<ProductAddDto> products;


    public static PostEntity of(UserEntity user, List<String> imgUrls, PostCreateDto postCreateDto) {

        PostEntity postEntity = PostEntity.builder()
                .postId(postCreateDto.getPostId())
                .user(user)
                .title(postCreateDto.getTitle())
                .participants(postCreateDto.getParticipants())
                .place(postCreateDto.getPlace())
                .deadline(postCreateDto.getDeadline())
                .status(postCreateDto.getStatus())
                .purchaseStatus(postCreateDto.getPurchaseStatus())
                .category(postCreateDto.getCategory())
                .content(postCreateDto.getContent())
                .imgUrls(imgUrls)
                .build();

        List<ProductEntity> productEntities = postCreateDto.getProducts().stream()
                .map(ProductAddDto::from)
                .peek(productEntity -> productEntity.setPost(postEntity))
                .collect(Collectors.toList());

        postEntity.setProducts(productEntities);
        return postEntity;
    }
}

