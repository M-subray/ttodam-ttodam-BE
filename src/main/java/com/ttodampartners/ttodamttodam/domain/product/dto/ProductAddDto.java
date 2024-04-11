package com.ttodampartners.ttodamttodam.domain.product.dto;

import com.ttodampartners.ttodamttodam.domain.post.entity.PostEntity;
import com.ttodampartners.ttodamttodam.domain.product.entity.ProductEntity;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.util.List;


@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductAddDto {

    @NotBlank(message = "상품명을 입력해 주세요!")
    private String productName;

    @NotBlank(message = "상품의 총수량을 입력해 주세요!")
    private Integer count;

    private Long price;
    private String purchaseLink;
    private String productImgUrl;

    public static ProductEntity from(ProductAddDto productAddDto) {
        return ProductEntity.builder()
                .productName(productAddDto.getProductName())
                .count(productAddDto.getCount())
                .purchaseLink(productAddDto.getPurchaseLink())
                .price(productAddDto.getPrice())
                .productImgUrl(productAddDto.getProductImgUrl())
                .build();
    }

}
