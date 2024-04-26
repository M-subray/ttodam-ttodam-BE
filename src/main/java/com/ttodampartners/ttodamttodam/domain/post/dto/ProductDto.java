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
    private String productName;
    private Long price;
    private Integer count;

    public static ProductDto from(ProductEntity productEntity){
        return ProductDto.builder()
                .productName(productEntity.getProductName())
                .price(productEntity.getPrice())
                .count(productEntity.getCount())
                .build();
    }

}
