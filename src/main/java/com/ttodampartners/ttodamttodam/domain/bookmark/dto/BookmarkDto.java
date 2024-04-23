package com.ttodampartners.ttodamttodam.domain.bookmark.dto;

import com.ttodampartners.ttodamttodam.domain.bookmark.entity.BookmarkEntity;
import com.ttodampartners.ttodamttodam.domain.post.dto.ProductDto;
import lombok.*;
import java.util.List;
import java.util.stream.Collectors;


@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BookmarkDto {
    private Long userId;
    private Long postId;
    private String postTitle;
    private List<ProductDto> products;

    public static BookmarkDto of(BookmarkEntity bookmarkEntity) {
        List<ProductDto> products = bookmarkEntity.getPost().getProducts()
                .stream()
                .map(ProductDto::from)
                .collect(Collectors.toList());

        return BookmarkDto.builder()
                .userId(bookmarkEntity.getUser().getId())
                .postId(bookmarkEntity.getPost().getPostId())
                .postTitle(bookmarkEntity.getPost().getTitle())
                .products(products)
                .build();
    }
}
