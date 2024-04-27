package com.ttodampartners.ttodamttodam.domain.post.dto;

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
