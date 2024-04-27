package com.ttodampartners.ttodamttodam.domain.chat.dto.response;

import lombok.*;

import java.util.List;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ChatroomEnterResponse {
    private List<ChatroomProfileResponse> profiles;
}
