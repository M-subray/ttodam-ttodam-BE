package com.ttodampartners.ttodamttodam.domain.post.dto;

import com.ttodampartners.ttodamttodam.domain.post.entity.PostEntity;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PostCreateDto {

    @NotBlank(message = "제목을 입력해 주세요!")
    private String title;

    @NotBlank(message = "희망 인원을 입력해 주세요!")
    private Integer participants;

    @NotBlank(message = "만남장소를 입력해 주세요!")
    private String place;

    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm")
    private LocalDateTime deadline;

    private String category;
    private String content;

    private String productName;
    private String purchaseLink;
    private Long price;
    private String productImgUrl;


    public static PostEntity from(PostCreateDto postCreateDto) {
        return PostEntity.builder()
                .title(postCreateDto.getTitle())
                .participants(postCreateDto.getParticipants())
                .place(postCreateDto.getPlace())
                .deadline(postCreateDto.getDeadline())
                .category(postCreateDto.getCategory())
                .content(postCreateDto.getContent())

                .productName(postCreateDto.getProductName())
                .purchaseLink(postCreateDto.getPurchaseLink())
                .price(postCreateDto.getPrice())
                .productImgUrl(postCreateDto.getProductImgUrl())
                .build();
    }

}
