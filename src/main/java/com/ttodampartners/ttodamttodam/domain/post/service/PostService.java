package com.ttodampartners.ttodamttodam.domain.post.service;

import com.ttodampartners.ttodamttodam.domain.post.dto.PostCreateDto;
import com.ttodampartners.ttodamttodam.domain.post.entity.PostEntity;
import com.ttodampartners.ttodamttodam.domain.post.repository.PostRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@RequiredArgsConstructor
@Service
public class PostService {

    private final PostRepository postRepository;

    @Transactional
    public PostEntity createPost(PostCreateDto postCreateDto) {
//        UserEntity userEntity = getUser(userId);

        PostEntity postEntity = PostCreateDto.from(postCreateDto);
        return postRepository.save(postEntity);
        }

    }
