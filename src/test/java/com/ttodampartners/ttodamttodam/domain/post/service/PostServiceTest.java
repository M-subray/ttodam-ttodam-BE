package com.ttodampartners.ttodamttodam.domain.post.service;

import com.ttodampartners.ttodamttodam.domain.post.dto.PostCreateDto;
import com.ttodampartners.ttodamttodam.domain.post.entity.PostEntity;
import com.ttodampartners.ttodamttodam.domain.post.repository.PostRepository;
import com.ttodampartners.ttodamttodam.domain.post.dto.ProductAddDto;
import com.ttodampartners.ttodamttodam.domain.user.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class PostServiceTest {

    @Autowired
    private PostService postService;
    @Autowired
    private PostRepository postRepository;
    @Autowired
    private UserRepository userRepository;

    @Test
    void CREATE_POST_TEST(){
        // 테스트 상품 생성
        ProductAddDto testProduct = testProduct("test product");
        // 테스트 게시물 생성
        PostCreateDto testPost = testPost("test title5",testProduct);

        // 가짜 이미지 파일 생성
        List<MultipartFile> imageFiles = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            byte[] imageBytes = ("test image " + (i + 1)).getBytes();
            MultipartFile imageFile = new MockMultipartFile("imageFiles", "test-image-" + (i + 1) + ".jpg", "image/jpeg", imageBytes);
            imageFiles.add(imageFile);
        }

        PostEntity post = postService.createPost(3L, imageFiles, testPost);

        Optional<PostEntity> optionalPost = postRepository.findById(post.getPostId());
        assertTrue(optionalPost.isPresent());

        PostEntity result = optionalPost.get();
        assertEquals("test title5", result.getTitle());
    }
    private static PostCreateDto testPost(String title, ProductAddDto product){
        return PostCreateDto.builder()
                .title(title)
                .participants(2)
                .place("서울특별시 중구 소공동 세종대로18길 2")
                .deadline(LocalDateTime.of(2024, 4, 17, 0, 0))
                .status(PostEntity.Status.IN_PROGRESS)
                .category(PostEntity.Category.DAILY)
                .content("test content")
                .products(Collections.singletonList(product))
                .build();
    }
    private static ProductAddDto testProduct(String productName){
        return ProductAddDto.builder()
                .productName(productName)
                .count(10)
                .price(10000L)
                .purchaseLink(":https://smartstore.naver.com/sskorea777/products/6364217638")
                .build();
    }


    @Test
    void GET_POST_LIST_TEST(){

    }

    @Test
    void GET_POST_TEST(){

    }

    @Test
    void UPDATE_POST_TEST(){

    }

    @Test
    void DELETE_POST_TEST(){

    }


}