package com.ttodampartners.ttodamttodam.domain.request.service;

import com.ttodampartners.ttodamttodam.domain.post.entity.PostEntity;
import com.ttodampartners.ttodamttodam.domain.post.repository.PostRepository;
import com.ttodampartners.ttodamttodam.domain.request.dto.RequestDto;
import com.ttodampartners.ttodamttodam.domain.request.dto.RequestSendDto;
import com.ttodampartners.ttodamttodam.domain.request.entity.RequestEntity;
import com.ttodampartners.ttodamttodam.domain.request.repository.RequestRepository;
import com.ttodampartners.ttodamttodam.domain.user.entity.UserEntity;
import com.ttodampartners.ttodamttodam.domain.user.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.stream.Collectors;

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

    @Transactional
    public List<RequestDto> getRequestList(Long postId) {
        List<RequestEntity> requestList = requestRepository.findAllByPost_postId(postId);
        return requestList.stream()
                .map(RequestDto::of)
                .collect(Collectors.toList());
    }

    @Transactional
    public RequestEntity updateRequestStatus(Long requestId,String requestStatus){
        RequestEntity  request = requestRepository.findById(requestId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Request not found"));

        RequestEntity.RequestStatus status = RequestEntity.RequestStatus.fromLabel(requestStatus);
        request.setRequestStatus(status);
        return request;
    }


}
