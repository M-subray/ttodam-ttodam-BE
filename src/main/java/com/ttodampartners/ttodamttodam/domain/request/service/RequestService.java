package com.ttodampartners.ttodamttodam.domain.request.service;

import com.ttodampartners.ttodamttodam.domain.bookmark.exception.BookmarkException;
import com.ttodampartners.ttodamttodam.domain.post.entity.PostEntity;
import com.ttodampartners.ttodamttodam.domain.post.exception.PostException;
import com.ttodampartners.ttodamttodam.domain.post.repository.PostRepository;
import com.ttodampartners.ttodamttodam.domain.request.dto.RequestDto;
import com.ttodampartners.ttodamttodam.domain.request.dto.RequestSendDto;
import com.ttodampartners.ttodamttodam.domain.request.entity.RequestEntity;
import com.ttodampartners.ttodamttodam.domain.request.exception.RequestException;
import com.ttodampartners.ttodamttodam.domain.request.repository.RequestRepository;
import com.ttodampartners.ttodamttodam.domain.user.entity.UserEntity;
import com.ttodampartners.ttodamttodam.domain.user.exception.UserException;
import com.ttodampartners.ttodamttodam.domain.user.repository.UserRepository;
import com.ttodampartners.ttodamttodam.global.error.ErrorCode;
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
                .orElseThrow(() -> new UserException(ErrorCode.NOT_FOUND_USER));

        PostEntity post = postRepository.findById(postId)
                .orElseThrow(() -> new PostException(ErrorCode.NOT_FOUND_POST));

        // 게시글의 상태에 따른 참여요청 응답
        if (post.getStatus() == PostEntity.Status.COMPLETED) {
            throw new RequestException(ErrorCode.POST_STATUS_COMPLETED);
        } else if (post.getStatus() == PostEntity.Status.FAILED) {
            throw new RequestException(ErrorCode.POST_STATUS_FAILED);
        }

        RequestEntity request = RequestSendDto.of(requestUser,post,requestSendDto);

        return requestRepository.save(request);
    }

    @Transactional
    public List<RequestDto> getRequestList(Long userId, Long postId) {
        List<RequestEntity> requestList = requestRepository.findAllByPost_postId(postId);

        PostEntity post = postRepository.findById(postId)
                .orElseThrow(() -> new PostException(ErrorCode.NOT_FOUND_POST));

        // 주최자 인증
        validateAuthority(userId, post);

        return requestList.stream()
                .map(RequestDto::of)
                .collect(Collectors.toList());
    }

    @Transactional
    public RequestEntity updateRequestStatus(Long userId, Long requestId,String requestStatus){
        RequestEntity  request = requestRepository.findById(requestId)
                .orElseThrow(() -> new RequestException(ErrorCode.NOT_FOUND_REQUEST));

        PostEntity post = request.getPost();

        // 주최자 인증
        validateAuthority(userId, post);

        if (post.getStatus() == PostEntity.Status.COMPLETED) {
            throw new RequestException(ErrorCode.POST_STATUS_COMPLETED);
        } else if (post.getStatus() == PostEntity.Status.FAILED) {
            throw new RequestException(ErrorCode.POST_STATUS_FAILED);
        }

        RequestEntity.RequestStatus status = RequestEntity.RequestStatus.fromLabel(requestStatus);
        request.setRequestStatus(status);

        updatePostStatus(post.getPostId());

        return request;
    }

    @Transactional
    public void updatePostStatus(Long postId) {
        PostEntity post = postRepository.findById(postId)
                .orElseThrow(() -> new PostException(ErrorCode.NOT_FOUND_POST));

        // 특정 게시글의 참여 요청 목록 조회
        List<RequestEntity> requests = requestRepository.findAllByPost_postId(postId);

        // 수락된 요청 계산
        long acceptedRequestsCount = requests.stream()
                .filter(request -> request.getRequestStatus() == RequestEntity.RequestStatus.ACCEPT)
                .count();

        // 게시글의 희망 인원과 비교
        if (acceptedRequestsCount == post.getParticipants()) {
            post.setStatus(PostEntity.Status.COMPLETED);

            // 남은 모든 요청들의 상태를 거절로 변경
            for (RequestEntity request : requests) {
                if (request.getRequestStatus() == RequestEntity.RequestStatus.WAIT) {
                    request.setRequestStatus(RequestEntity.RequestStatus.REFUSE);
                }
            }
        }
    }

    @Transactional
    public void deleteRequest(Long userId, Long requestId) {
        RequestEntity request = requestRepository.findById(requestId)
                .orElseThrow(() -> new RequestException(ErrorCode.NOT_FOUND_REQUEST));

        UserEntity requestUser = userRepository.findById(userId)
                .orElseThrow(() -> new UserException(ErrorCode.NOT_FOUND_USER));

        Long requestUserId = requestUser.getId();

        // 권한 인증
        if (!userId.equals(requestUserId)) {
            throw new RequestException(ErrorCode.REQUEST_PERMISSION_DENIED);
        }

        requestRepository.delete(request);
    }

    private void validateAuthority(Long userId, PostEntity post) {
        userRepository.findById(userId)
                .orElseThrow(() -> new UserException(ErrorCode.NOT_FOUND_USER));

        Long postAuthorId = post.getUser().getId();

        if (!userId.equals(postAuthorId)) {
            throw new PostException(ErrorCode.POST_PERMISSION_DENIED);
        }
    }


}
