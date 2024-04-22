package com.ttodampartners.ttodamttodam.domain.chat.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

/*
 * 채팅방 생성 request body
*/
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class ChatroomCreateRequest {
    @NotBlank(message = "userId 이상")
    private Long userId;
    @NotBlank(message = "postId 이상")
    private Long postId;
}
