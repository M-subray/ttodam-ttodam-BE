package com.ttodampartners.ttodamttodam.domain.request.service;

import com.ttodampartners.ttodamttodam.domain.post.entity.PostEntity;
import com.ttodampartners.ttodamttodam.domain.request.dto.RequestDto;
import com.ttodampartners.ttodamttodam.domain.request.dto.RequestSendDto;
import com.ttodampartners.ttodamttodam.domain.request.entity.RequestEntity;
import com.ttodampartners.ttodamttodam.domain.request.repository.RequestRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
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
        RequestEntity request = requestService.sendRequest(3L,57L,testRequest);

        Optional<RequestEntity> optionalRequest = requestRepository.findById(request.getRequestId());
        assertTrue(optionalRequest.isPresent());
    }
    private static RequestSendDto testRequest(){
        return RequestSendDto.builder()
                .requestStatus(RequestEntity.RequestStatus.WAIT)
                .build();
    }

    @Test
    void GET_REQUEST_LIST_TEST(){

        Long postId = 57L;

        List<RequestDto> requestList = requestService.getRequestList(postId);

        assertEquals(2, requestList.size());
    }

    @Test
    void UPDATE_REQUEST_STATUS_TEST(){

        RequestEntity updateRequest = requestService.updateRequestStatus(2L,"수락");

        Optional<RequestEntity> optionalRequest = requestRepository.findById(updateRequest.getRequestId());
        assertTrue(optionalRequest.isPresent(), "Updated request should exist in the database");

        RequestEntity result = optionalRequest.get();
        assertEquals(RequestEntity.RequestStatus.ACCEPT, result.getRequestStatus());
    }

}