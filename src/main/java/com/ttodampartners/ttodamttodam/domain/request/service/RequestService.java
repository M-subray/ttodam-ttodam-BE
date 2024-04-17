package com.ttodampartners.ttodamttodam.domain.request.service;

import com.ttodampartners.ttodamttodam.domain.post.entity.PostEntity;
import com.ttodampartners.ttodamttodam.domain.post.repository.PostRepository;
import com.ttodampartners.ttodamttodam.domain.request.dto.RequestSendDto;
import com.ttodampartners.ttodamttodam.domain.request.entity.RequestEntity;
import com.ttodampartners.ttodamttodam.domain.request.repository.RequestRepository;
import com.ttodampartners.ttodamttodam.domain.user.entity.UserEntity;
import com.ttodampartners.ttodamttodam.domain.user.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class RequestService {

    private final RequestRepository requestRepository;
    private final PostRepository postRepository;
    private final UserRepository userRepository;

    @Transactional
    public RequestEntity sendRequest(Long requestUserId, Long postId, RequestSendDto requestSendDto){
        UserEntity requestUser = userRepository.findById(requestUserId)
                .orElseThrow(() -> new IllegalArgumentException("해당하는 사용자를 찾을 수 없습니다."));

        PostEntity post = postRepository.findById(postId)
                .orElseThrow(() -> new IllegalArgumentException("해당하는 게시글을 찾을 수 없습니다."));

        RequestEntity request = RequestSendDto.of(requestUser,post,requestSendDto);

        return requestRepository.save(request);
    }

}
