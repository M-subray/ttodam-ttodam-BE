package com.ttodampartners.ttodamttodam.domain.chat.dto.event;

import com.ttodampartners.ttodamttodam.domain.post.entity.PostEntity;
import com.ttodampartners.ttodamttodam.domain.participation.entity.ParticipationEntity;
import lombok.*;

import java.util.List;

@Getter
@Builder
public class GroupChatCreateEvent {
    private final PostEntity post;
    private final List<ParticipationEntity> requestEntities;
}
