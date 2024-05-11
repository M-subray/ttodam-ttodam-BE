package com.ttodampartners.ttodamttodam.domain.bookmark.dto;

import com.ttodampartners.ttodamttodam.domain.bookmark.entity.BookmarkEntity;
import com.ttodampartners.ttodamttodam.domain.post.dto.ProductListDto;
import lombok.*;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BookmarkDto {

  private Long bookmarkId;
  private Long userId;
  private Long postId;
  private String postTitle;
  private String postStatus;
  private List<ProductListDto> products;

  public static BookmarkDto of(BookmarkEntity bookmarkEntity) {
    String postStatus = bookmarkEntity.getPost().getStatus().toString();
    List<ProductListDto> products = bookmarkEntity.getPost().getProducts()
        .stream()
        .map(ProductListDto::from)
        .collect(Collectors.toList());

    return BookmarkDto.builder()
        .bookmarkId(bookmarkEntity.getBookmarkId())
        .userId(bookmarkEntity.getUser().getId())
        .postId(bookmarkEntity.getPost().getPostId())
        .postTitle(bookmarkEntity.getPost().getTitle())
        .postStatus(postStatus)
        .products(products)
        .build();
  }
}
