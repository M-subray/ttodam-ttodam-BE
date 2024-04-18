package com.ttodampartners.ttodamttodam.domain.chat.entity;

import com.ttodampartners.ttodamttodam.domain.user.entity.UserEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "chatroom_member")
public class ChatroomMemberEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long chatroomMemberId;

    // CHATROOMS 테이블과 연결
    @ManyToOne
    @JoinColumn(name = "chatroom_id", referencedColumnName = "id", nullable = false)
    private ChatroomEntity chatroomEntity;

    // USER 테이블과 연결
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private UserEntity userEntity;

    @Column(name = "chat_active")
    private boolean chatActive;

    public ChatroomMemberEntity(boolean chatActive) {
        this.chatActive = false;
    }
}
