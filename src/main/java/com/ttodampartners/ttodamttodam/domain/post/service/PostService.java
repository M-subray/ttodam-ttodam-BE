package com.ttodampartners.ttodamttodam.domain.post.service;

import com.ttodampartners.ttodamttodam.domain.post.dto.PostCreateDto;
import com.ttodampartners.ttodamttodam.domain.post.dto.PostDto;
import com.ttodampartners.ttodamttodam.domain.post.dto.PostUpdateDto;
import com.ttodampartners.ttodamttodam.domain.post.entity.PostEntity;
import com.ttodampartners.ttodamttodam.domain.post.repository.PostRepository;
import com.ttodampartners.ttodamttodam.domain.product.dto.ProductUpdateDto;
import com.ttodampartners.ttodamttodam.domain.product.entity.ProductEntity;
import com.ttodampartners.ttodamttodam.domain.user.entity.UserEntity;
import com.ttodampartners.ttodamttodam.domain.user.repository.UserRepository;
import com.ttodampartners.ttodamttodam.domain.user.util.CoordinateFinderUtil;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class PostService {

    private final PostRepository postRepository;
    private final UserRepository userRepository;
    private final CoordinateFinderUtil coordinateFinderUtil;

    // userID 추가
    @Transactional
    public PostEntity createPost(Long userId, PostCreateDto postCreateDto) {
        UserEntity user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("해당하는 사용자를 찾을 수 없습니다."));

        try {
            PostEntity post = PostCreateDto.of(user, postCreateDto);

            double[] coordinates = coordinateFinderUtil.getCoordinates(postCreateDto.getPlace());
            post.setPLocationX(coordinates[1]); // 경도 설정
            post.setPLocationY(coordinates[0]); // 위도 설정

            return postRepository.save(post);
        } catch (IOException e) {
            throw new RuntimeException("위치 정보를 가져오는 동안 에러가 발생했습니다.", e);
        }
    }

    @Transactional
    public List<PostDto> getPostList() {
        List<PostEntity> postList = postRepository.findAll();
        return postList.stream()
                .map(PostDto::of)
                .collect(Collectors.toList());
    }

    @Transactional
    public PostDto getPost(Long postId) {
        PostEntity post = postRepository.findById(postId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Post not found"));
        return PostDto.of(post);
    }

     // userID 추가
    @Transactional
    public PostEntity updatePost(Long postId, PostUpdateDto postUpdateDto) {
        //        UserEntity userEntity = getUser(userId);
        PostEntity post = postRepository.findById(postId)
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

//    private UserEntity getUser(Long userId) {
//        return userRepository.findById(userId).orElseThrow(
//                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Post not found"));
//    }

    }
