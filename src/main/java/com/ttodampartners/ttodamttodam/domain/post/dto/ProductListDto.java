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
    private Integer count;
    private Long price;

    public static ProductListDto from(ProductEntity productEntity){
        return ProductListDto.builder()
                .productName(productEntity.getProductName())
                .count(productEntity.getCount())
                .price(productEntity.getPrice())
                .build();
    }

}
