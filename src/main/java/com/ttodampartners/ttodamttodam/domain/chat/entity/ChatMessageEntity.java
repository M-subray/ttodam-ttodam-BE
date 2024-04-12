package com.ttodampartners.ttodamttodam.domain.chat.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@AttributeOverride(name = "createAt", column = @Column(name = "created_time"))
@Entity(name = "chat_message")
public class ChatMessageEntity extends BaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long chatMessageId;

    // USER 테이블과 연결
    @ManyToOne
    @JoinColumn(name = "sender_id", nullable = false)
    private UserEntity userEntity;

    // CHATROOMS 테이블과 연결
    @ManyToOne
    @JoinColumn(name = "chatroom_id", referencedColumnName = "id", nullable = false)
    private ChatroomsEntity chatroomsEntity;

    @Column
    private String content;
}
