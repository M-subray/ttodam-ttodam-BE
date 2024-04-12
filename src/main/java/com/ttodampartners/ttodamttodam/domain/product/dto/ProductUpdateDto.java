package com.ttodampartners.ttodamttodam.domain.product.dto;

import com.ttodampartners.ttodamttodam.domain.product.entity.ProductEntity;
import jakarta.validation.constraints.NotBlank;
import lombok.*;


@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductUpdateDto {

    private Long productId;

    private String productName;

    private Integer count;

    private Long price;

    private String purchaseLink;

}
