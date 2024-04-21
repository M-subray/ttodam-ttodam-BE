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
            @RequestPart(value = "imageFiles", required = false) List<MultipartFile> imageFiles,
            @RequestBody PostCreateDto postCreateDto
        ) {
        Long userId = userDetails.getId();
        return ResponseEntity.ok(PostDto.of(postService.createPost(userId, imageFiles, postCreateDto)));
       }


    @GetMapping("/post")
    public ResponseEntity<List<PostDto>> getPostList(
            @AuthenticationPrincipal UserDetailsDto userDetails
    ){
        Long userId = userDetails.getId();
        List<PostDto> postList = postService.getPostList(userId);
        return ResponseEntity.ok(postList);
    }

    @GetMapping("/post/{postId}")
    public ResponseEntity<PostDto> getPost(
            @AuthenticationPrincipal UserDetailsDto userDetails,
            @PathVariable Long postId
    )
    {
        Long userId = userDetails.getId();
        PostDto postDto = postService.getPost(userId, postId);
        return ResponseEntity.status(OK).body(postDto);
    }

    @PutMapping("/post/{postId}")
    public ResponseEntity<PostDto> updatePost(
            @AuthenticationPrincipal UserDetailsDto userDetails,
            @PathVariable Long postId,
            @RequestPart(value = "imageFiles", required = false) List<MultipartFile> newImageFiles,
            @RequestBody PostUpdateDto postUpdateDto
    )
    {
        Long userId = userDetails.getId();
        return ResponseEntity.ok(PostDto.of(postService.updatePost(userId, postId, newImageFiles, postUpdateDto)));
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
