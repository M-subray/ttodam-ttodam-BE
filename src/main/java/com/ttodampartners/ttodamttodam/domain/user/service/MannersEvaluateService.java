package com.ttodampartners.ttodamttodam.domain.user.service;

import com.ttodampartners.ttodamttodam.domain.post.entity.PostEntity;
import com.ttodampartners.ttodamttodam.domain.post.exception.PostException;
import com.ttodampartners.ttodamttodam.domain.post.repository.PostRepository;
import com.ttodampartners.ttodamttodam.domain.request.exception.RequestException;
import com.ttodampartners.ttodamttodam.domain.user.dto.MannersDto;
import com.ttodampartners.ttodamttodam.domain.user.entity.UserEntity;
import com.ttodampartners.ttodamttodam.domain.user.exception.UserException;
import com.ttodampartners.ttodamttodam.domain.user.repository.UserRepository;
import com.ttodampartners.ttodamttodam.global.error.ErrorCode;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;


@RequiredArgsConstructor
@Service
public class MannersEvaluateService {

    private final UserRepository userRepository;
    private final PostRepository postRepository;

    @Transactional
    public UserEntity evaluateManners (Long userId, Long postId, Long memberId, MannersDto mannersDto) {
        UserEntity user = userRepository.findById(userId)
                .orElseThrow(() -> new UserException(ErrorCode.NOT_FOUND_USER));

        UserEntity member = userRepository.findById(memberId)
                .orElseThrow(() -> new UserException(ErrorCode.NOT_FOUND_USER));

        PostEntity post = postRepository.findById(postId)
                .orElseThrow(() -> new PostException(ErrorCode.NOT_FOUND_POST));

        List<UserEntity> postMembers = userRepository.findAcceptedRequestUsersByPostId(postId);

        // memberId에 해당하는 회원이 수락된 요청자 목록에 포함되어 있는지 확인
        boolean isMembers = postMembers.stream()
                .anyMatch(members -> members.getId().equals(memberId));
        if (!isMembers) {
            throw new RequestException(ErrorCode.NOT_ACCEPTED_MEMBER);
        }

        // 로그인된 유저가 수락된 요청자 목록에 포함되어 있는지 확인
        boolean isUserMember = postMembers.stream()
                .anyMatch(userMember -> userMember.getId().equals(userId));
        if (!isUserMember) {
            throw new RequestException(ErrorCode.NOT_ACCEPTED_MEMBER);
        }

        // 게시글 공구상태 확인
        if (post.getPurchaseStatus() != PostEntity.PurchaseStatus.SUCCESS) {
            throw new RequestException(ErrorCode.POST_PURCHASE_STATUS_NOT_SUCCESS);
        }

        // 이미 평가를 완료하였는지 체크(같은 게시글에는 중복으로 평가할 수 없어야함.)

        // 나중에는 평점으로 저장하기_평가받은 횟수 필요...
        member.setManners(mannersDto.getManners());


        return member;
    }
}
