package com.ttodampartners.ttodamttodam.domain.product.controller;

import com.ttodampartners.ttodamttodam.domain.post.dto.PostCreateDto;
import com.ttodampartners.ttodamttodam.domain.post.dto.PostDto;
import com.ttodampartners.ttodamttodam.domain.post.service.PostService;
import com.ttodampartners.ttodamttodam.domain.product.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.HttpStatus.OK;


@RequiredArgsConstructor
@RestController
public class ProductController {
    private final ProductService productService;

    @DeleteMapping("/post/product/{productId}")
    public ResponseEntity<Void> deleteProduct(
            @PathVariable Long productId
    )
    {
        productService.deleteProduct(productId);
        return ResponseEntity.status(OK).build();
    }

}
