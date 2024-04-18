package com.ttodampartners.ttodamttodam.domain.post.controller;

import com.ttodampartners.ttodamttodam.domain.post.dto.PostCreateDto;
import com.ttodampartners.ttodamttodam.domain.post.dto.PostDto;
import com.ttodampartners.ttodamttodam.domain.post.dto.PostUpdateDto;
import com.ttodampartners.ttodamttodam.domain.post.service.PostService;
import com.ttodampartners.ttodamttodam.global.dto.UserDetailsDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

import static org.springframework.http.HttpStatus.*;


@RequiredArgsConstructor
@RestController
public class PostController {
    private final PostService postService;

    @PostMapping("/post")
    public ResponseEntity<PostDto> createPost(
            @AuthenticationPrincipal UserDetailsDto userDetails,
            @RequestPart(value = "imageFile", required = false) MultipartFile imageFile,
            @RequestBody PostCreateDto postCreateDto
        ) {
        Long userId = userDetails.getId();
        return ResponseEntity.ok(PostDto.of(postService.createPost(userId, imageFile, postCreateDto)));
       }


    @GetMapping("/post")
    public ResponseEntity<List<PostDto>> getPostList(

    ){
        List<PostDto> postList = postService.getPostList();
        return ResponseEntity.ok(postList);
    }

    @GetMapping("/post/{postId}")
    public ResponseEntity<PostDto> getPost(
            @PathVariable Long postId
    )
    {
        PostDto postDto = postService.getPost(postId);
        return ResponseEntity.status(OK).body(postDto);
    }

    @PutMapping("/post/{postId}")
    public ResponseEntity<PostDto> updatePost(
            @RequestBody PostUpdateDto postUpdateDto,
            @PathVariable Long postId
    )
    {
        return ResponseEntity.ok(PostDto.of(postService.updatePost(postId, postUpdateDto)));
    }

    @DeleteMapping("/post/{postId}")
    public ResponseEntity<Void> deletePost(
            @PathVariable Long postId
    )
    {
        postService.deletePost(postId);
        return ResponseEntity.status(OK).build();
    }


}
