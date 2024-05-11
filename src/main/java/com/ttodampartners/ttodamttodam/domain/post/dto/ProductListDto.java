package com.ttodampartners.ttodamttodam.domain.post.dto;

import com.ttodampartners.ttodamttodam.domain.post.entity.ProductEntity;
import lombok.*;

@Getter
@Setter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class ProductListDto {

    private String productName;
    private Long price;
    private Integer count;

    public static ProductListDto from(ProductEntity productEntity) {
        return ProductListDto.builder()
            .productName(productEntity.getProductName())
            .price(productEntity.getPrice())
            .count(productEntity.getCount())
            .build();
    }
}
