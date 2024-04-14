package com.ttodampartners.ttodamttodam.domain.product.service;

import com.ttodampartners.ttodamttodam.domain.product.dto.ProductUpdateDto;
import com.ttodampartners.ttodamttodam.domain.product.entity.ProductEntity;
import com.ttodampartners.ttodamttodam.domain.product.repository.ProductRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@RequiredArgsConstructor
@Service
public class ProductService {

    private final ProductRepository productRepository;

    @Transactional
    public ProductEntity updateProduct(ProductUpdateDto productUpdateDto) {
        ProductEntity product = productRepository.findById(productUpdateDto.getProductId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Post not found"));
        product.setProductName(productUpdateDto.getProductName());
        product.setCount(productUpdateDto.getCount());
        product.setPrice(productUpdateDto.getPrice());
        product.setPurchaseLink(productUpdateDto.getPurchaseLink());
        return product;
    }

    @Transactional
    public void deleteProduct(Long productId) {
        ProductEntity product = productRepository.findById(productId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Post not found"));
        productRepository.delete(product);
    }

    }
