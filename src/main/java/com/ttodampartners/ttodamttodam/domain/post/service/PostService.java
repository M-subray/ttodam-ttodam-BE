package com.ttodampartners.ttodamttodam.domain.post.service;

import com.ttodampartners.ttodamttodam.domain.post.dto.PostCreateDto;
import com.ttodampartners.ttodamttodam.domain.post.dto.PostDto;
import com.ttodampartners.ttodamttodam.domain.post.dto.PostUpdateDto;
import com.ttodampartners.ttodamttodam.domain.post.entity.PostEntity;
import com.ttodampartners.ttodamttodam.domain.post.repository.PostRepository;
import com.ttodampartners.ttodamttodam.domain.product.dto.ProductAddDto;
import com.ttodampartners.ttodamttodam.domain.product.dto.ProductDto;
import com.ttodampartners.ttodamttodam.domain.product.dto.ProductUpdateDto;
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
    @Transactional
    public PostEntity updatePost(PostUpdateDto postUpdateDto) {
        //        UserEntity userEntity = getUser(userId);
        PostEntity post = postRepository.findById(postUpdateDto.getPostId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Post not found"));
        post.setTitle(postUpdateDto.getTitle());
        post.setParticipants(postUpdateDto.getParticipants());
        post.setPlace(postUpdateDto.getPlace());
        post.setDeadline(postUpdateDto.getDeadline());
        post.setCategory(postUpdateDto.getCategory());
        post.setContent(postUpdateDto.getContent());

        for(ProductUpdateDto productUpdateDto : postUpdateDto.getProducts()){
            ProductEntity product = post.getProducts().stream()
                    .filter(pi->pi.getProductId().equals(productUpdateDto.getProductId()))
                    .findFirst().orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Product not found"));
            product.setProductName(productUpdateDto.getProductName());
            product.setCount(productUpdateDto.getCount());
            product.setPrice(productUpdateDto.getPrice());
            product.setPurchaseLink(productUpdateDto.getPurchaseLink());

        }
        return post;
    }

    // userID 추가
    @Transactional
    public void deletePost(Long postId) {
        PostEntity post = postRepository.findById(postId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Post not found"));
        postRepository.delete(post);
    }

    }
