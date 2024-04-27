package com.ttodampartners.ttodamttodam.domain.post.dto;

import com.ttodampartners.ttodamttodam.domain.post.entity.ProductEntity;
import lombok.*;

@Getter
@Setter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class ProductsDto {
    private Long productId;
    private Long postId;
    private String productName;
    private Integer count;
    private String purchaseLink;
    private Long price;

    public static ProductsDto from(ProductEntity productEntity){
        return ProductsDto.builder()
                .productId(productEntity.getProductId())
                .postId(productEntity.getPost().getPostId())
                .productName(productEntity.getProductName())
                .count(productEntity.getCount())
                .purchaseLink(productEntity.getPurchaseLink())
                .price(productEntity.getPrice())
                .build();
    }

}
