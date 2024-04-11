package com.ttodampartners.ttodamttodam.domain.product.dto;

import com.ttodampartners.ttodamttodam.domain.product.entity.ProductEntity;
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
    private String productImgUrl;

    public static ProductDto from(ProductEntity productEntity){
        return ProductDto.builder()
                .productId(productEntity.getProductId())
                .postId(productEntity.getPost().getPostId())
                .productName(productEntity.getProductName())
                .count(productEntity.getCount())
                .purchaseLink(productEntity.getPurchaseLink())
                .price(productEntity.getPrice())
                .productImgUrl(productEntity.getProductImgUrl())
                .build();
    }

}
