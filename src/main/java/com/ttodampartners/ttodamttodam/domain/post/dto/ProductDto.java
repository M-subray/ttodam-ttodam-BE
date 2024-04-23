package com.ttodampartners.ttodamttodam.domain.post.dto;

import com.ttodampartners.ttodamttodam.domain.post.entity.ProductEntity;
import lombok.*;

@Getter
@Setter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class ProductDto {
    private Long productId;
    private Long postId;
    private String productName;
    private Integer count;
    private String purchaseLink;
    private Long price;

    public static ProductDto from(ProductEntity productEntity){
        return ProductDto.builder()
                .productId(productEntity.getProductId())
                .postId(productEntity.getPost().getPostId())
                .productName(productEntity.getProductName())
                .count(productEntity.getCount())
                .purchaseLink(productEntity.getPurchaseLink())
                .price(productEntity.getPrice())
                .build();
    }

}
