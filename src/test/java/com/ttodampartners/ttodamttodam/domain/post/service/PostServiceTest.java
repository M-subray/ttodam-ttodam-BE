package com.ttodampartners.ttodamttodam.domain.post.service;

import com.ttodampartners.ttodamttodam.domain.post.dto.PostCreateDto;
import com.ttodampartners.ttodamttodam.domain.post.entity.PostEntity;
import com.ttodampartners.ttodamttodam.domain.post.repository.PostRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class PostServiceTest {

    @Autowired
    private PostService postService;
    @Autowired
    private PostRepository postRepository;

    @Test
    void CREATE_POST_TEST(){

        LocalDateTime deadline = LocalDateTime.of(2024, 4, 5, 0, 0);

        PostCreateDto postCreateDto = new PostCreateDto("test title", 2, "test place", deadline, "c", "test content", "test productName", "test purchaseLink", 10000L, "test productImgUrl");


        PostEntity post = postService.createPost(postCreateDto);

        assertNotNull(post);
        assertNotNull(post.getPostId());

        Optional<PostEntity> optionalPost = postRepository.findById(post.getPostId());
        assertTrue(optionalPost.isPresent());

        PostEntity result = optionalPost.get();
        assertEquals("test title", result.getTitle());
    }
}