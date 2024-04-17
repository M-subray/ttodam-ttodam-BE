package com.ttodampartners.ttodamttodam.domain.request.service;

import com.ttodampartners.ttodamttodam.domain.post.dto.PostCreateDto;
import com.ttodampartners.ttodamttodam.domain.post.entity.PostEntity;
import com.ttodampartners.ttodamttodam.domain.post.repository.PostRepository;
import com.ttodampartners.ttodamttodam.domain.product.dto.ProductAddDto;
import com.ttodampartners.ttodamttodam.domain.request.dto.RequestSendDto;
import com.ttodampartners.ttodamttodam.domain.request.entity.RequestEntity;
import com.ttodampartners.ttodamttodam.domain.request.repository.RequestRepository;
import com.ttodampartners.ttodamttodam.domain.user.entity.UserEntity;
import com.ttodampartners.ttodamttodam.domain.user.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.NoSuchElementException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class RequestServiceTest {

    @Autowired
    private RequestService requestService;
    @Autowired
    private RequestRepository requestRepository;

    @Test
    void SEND_REQUEST_TEST(){

        // 테스트 참여요청 생성
        RequestSendDto testRequest = testRequest();
        RequestEntity request = requestService.sendRequest(2L,57L,testRequest);

        Optional<RequestEntity> optionalRequest = requestRepository.findById(request.getRequestId());
        assertTrue(optionalRequest.isPresent());
    }
    private static RequestSendDto testRequest(){
        return RequestSendDto.builder()
                .requestStatus(RequestEntity.RequestStatus.WAIT)
                .build();
    }

}