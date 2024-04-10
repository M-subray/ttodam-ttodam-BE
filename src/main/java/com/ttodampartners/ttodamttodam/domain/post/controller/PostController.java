package com.ttodampartners.ttodamttodam.domain.post.controller;

import com.ttodampartners.ttodamttodam.domain.post.dto.PostCreateDto;
import com.ttodampartners.ttodamttodam.domain.post.dto.PostDto;
import com.ttodampartners.ttodamttodam.domain.post.entity.PostEntity;
import com.ttodampartners.ttodamttodam.domain.post.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import static org.springframework.http.HttpStatus.*;


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
        return ResponseEntity.status(OK).build();
       }

//    @GetMapping("/post")
//    public ResponseEntity<PostDto> getPostList(
//
//    ){
//
//    }

    @GetMapping("/post/{postId}")
    public ResponseEntity<PostDto> getPost(
            @PathVariable Long postId
    )
    {
        PostDto postDto = postService.getPost(postId);
        return ResponseEntity.status(OK).body(postDto);
    }

//    @PutMapping("/post/{postId}")
//    public ResponseEntity<PostDto> updatePost(
//            @PathVariable Long postId
//    )
//    {
//        PostDto postDto = postService.updatePost(postId);
//        return ResponseEntity.status(OK).body(postDto);
//    }

    @DeleteMapping("/post/{postId}")
    public ResponseEntity<Void> deletePost(
            @PathVariable Long postId
    )
    {
        postService.deletePost(postId);
        return ResponseEntity.status(OK).build();
    }


}
