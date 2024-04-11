package com.ttodampartners.ttodamttodam.domain.post.service;

import com.ttodampartners.ttodamttodam.domain.post.dto.PostCreateDto;
import com.ttodampartners.ttodamttodam.domain.post.dto.PostDto;
import com.ttodampartners.ttodamttodam.domain.post.entity.PostEntity;
import com.ttodampartners.ttodamttodam.domain.post.repository.PostRepository;
import com.ttodampartners.ttodamttodam.domain.product.dto.ProductAddDto;
import com.ttodampartners.ttodamttodam.domain.product.dto.ProductDto;
import com.ttodampartners.ttodamttodam.domain.product.entity.ProductEntity;
import com.ttodampartners.ttodamttodam.domain.product.repository.ProductRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class PostService {

    private final PostRepository postRepository;
    private ProductRepository productRepository;

    // userID 추가
    @Transactional
    public PostEntity createPost(PostCreateDto postCreateDto) {
//        UserEntity userEntity = getUser(userId);

        PostEntity postEntity = PostCreateDto.from(postCreateDto);

        return postRepository.save(postEntity);
        }


    @Transactional
    public PostDto getPost(Long postId) {
        PostEntity post = postRepository.findById(postId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Post not found"));
        return PostDto.of(post);
    }

     // userID 추가
//    @Transactional
//    public PostEntity updatePost(Long postId) {
//        //        UserEntity userEntity = getUser(userId);
//
//        PostEntity postEntity = PostCreateDto.from(PostCreateDto);
//        return postRepository.save(postEntity);
//    }

    // userID 추가
    @Transactional
    public void deletePost(Long postId) {
        PostEntity post = postRepository.findById(postId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Post not found"));
        postRepository.delete(post);
    }

    }
