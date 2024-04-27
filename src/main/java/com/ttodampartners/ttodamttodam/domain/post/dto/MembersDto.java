package com.ttodampartners.ttodamttodam.domain.post.dto;

import com.ttodampartners.ttodamttodam.domain.user.entity.UserEntity;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MembersDto {
    private Long memberId;
    private String memberNickname;

    public static MembersDto of(UserEntity userEntity) {
        return MembersDto.builder()
                .memberId(userEntity.getId())
                .memberNickname(userEntity.getNickname())
                .build();
    }
}
