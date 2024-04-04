package com.ttodampartners.ttodamttodam.domain.post.controller;

import com.ttodampartners.ttodamttodam.domain.post.dto.PostCreateDto;
import com.ttodampartners.ttodamttodam.domain.post.dto.PostDto;
import com.ttodampartners.ttodamttodam.domain.post.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;


@RequiredArgsConstructor
@RestController
public class PostController {
    private final PostService postService;

    @PostMapping("/post")
    public ResponseEntity<PostDto> createPost(
            @RequestBody PostCreateDto postCreateDto
//                @AuthenticationPrincipal
        ) {
        postService.createPost(postCreateDto);
//        postService.createPost(userId, postCreateDto);
        return ResponseEntity.ok().build();
       }
}
