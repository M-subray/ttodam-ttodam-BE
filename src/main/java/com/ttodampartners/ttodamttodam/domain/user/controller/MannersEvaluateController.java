package com.ttodampartners.ttodamttodam.domain.user.controller;

import com.ttodampartners.ttodamttodam.domain.user.dto.MannersDto;
import com.ttodampartners.ttodamttodam.domain.user.service.MannersEvaluateService;
import com.ttodampartners.ttodamttodam.global.dto.UserDetailsDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;


@RequiredArgsConstructor
@RestController
public class MannersEvaluateController {

    private final MannersEvaluateService mannersEvaluateService;

    @PutMapping("/users/activities/{postId}/manners/{memberId}")
    public ResponseEntity<Void> evaluateManners(
            @AuthenticationPrincipal UserDetailsDto userDetails,
            @PathVariable Long postId,
            @PathVariable Long memberId,
            @RequestBody MannersDto mannersDto
    ) {
        Long userId = userDetails.getId();
        mannersEvaluateService.evaluateManners(userId, postId, memberId, mannersDto);
        return ResponseEntity.ok().build();
    }
}
