package com.ttodampartners.ttodamttodam.domain.chat.dto.event;

import com.ttodampartners.ttodamttodam.domain.post.entity.PostEntity;
import com.ttodampartners.ttodamttodam.domain.request.entity.RequestEntity;
import lombok.*;

import java.util.List;

@Getter
@Builder
@RequiredArgsConstructor
@AllArgsConstructor
public class GroupChatCreateEvent {
    private final PostEntity post;
    private final List<RequestEntity> requestEntities;
}
