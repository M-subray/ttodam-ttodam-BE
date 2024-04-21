package com.ttodampartners.ttodamttodam.domain.chat.entity;

import com.ttodampartners.ttodamttodam.domain.chat.dto.response.ChatMessageResponse;
import com.ttodampartners.ttodamttodam.domain.user.entity.UserEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EntityListeners(AuditingEntityListener.class)
@Entity(name = "chat_message")
public class ChatMessageEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long chatMessageId;

    // USER 테이블과 연결
    @ManyToOne
    @JoinColumn(name = "sender_id", nullable = false)
    private UserEntity userEntity;

    // CHATROOM 테이블과 연결
    @ManyToOne
    @JoinColumn(name = "chatroom_id", nullable = false)
    private ChatroomEntity chatroomEntity;

    @Column
    private String nickname;

    @Column
    private String content;

    @Column(name = "create_at", nullable = false)
    @CreatedDate
    private LocalDateTime createAt;

    // 채팅 메시지 내역 response 반환
    public ChatMessageResponse getChatMessageResponse() {
        ChatroomEntity chatroom = this.chatroomEntity;
        UserEntity user = this.userEntity;

        return ChatMessageResponse.builder()
                .messageId(this.chatMessageId)
                .chatroomId(chatroom.getChatroomId())
                .senderId(user.getId())
                .nickname(this.nickname)
                .content(this.content)
                .messageCreateAt(this.createAt)
                .build();
    }
}
