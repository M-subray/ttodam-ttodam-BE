package com.ttodampartners.ttodamttodam.domain.post.dto;

import com.ttodampartners.ttodamttodam.domain.post.entity.PostEntity;
import com.ttodampartners.ttodamttodam.domain.product.dto.ProductAddDto;
import com.ttodampartners.ttodamttodam.domain.product.dto.ProductUpdateDto;
import com.ttodampartners.ttodamttodam.domain.product.entity.ProductEntity;
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
public class PostUpdateDto {

    private Long postId;

    private String title;

    private Integer participants;

    private String place;

    private LocalDateTime deadline;

    private PostEntity.Category category;
    private String content;

    private List<ProductUpdateDto> products;


//    public static PostEntity from(PostUpdateDto postUpdateDto) {
//
//        List<ProductUpdateDto> products = postUpdateDto.getProducts();
//        if (products == null) {
//            products = Collections.emptyList();
//        }
//
//        PostEntity postEntity = PostEntity.builder()
//                .postId(postUpdateDto.getPostId())
//                .title(postUpdateDto.getTitle())
//                .participants(postUpdateDto.getParticipants())
//                .place(postUpdateDto.getPlace())
//                .deadline(postUpdateDto.getDeadline())
//                .category(postUpdateDto.getCategory())
//                .content(postUpdateDto.getContent())
//                .build();
//
//        List<ProductEntity> productEntities = products.stream()
//                .map(productUpdateDto -> {
//                    ProductEntity productEntity = ProductUpdateDto.from(productUpdateDto);
//                    productEntity.setPost(postEntity);
//                    return productEntity;
//                })
//                .collect(Collectors.toList());
//
//        postEntity.setProducts(productEntities);
//
//        return postEntity;
//    }

}
