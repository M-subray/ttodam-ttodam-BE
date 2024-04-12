package com.ttodampartners.ttodamttodam.domain.chat.entity;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EntityListeners(AuditingEntityListener.class)
@Entity(name = "CHATROOMS")
public class ChatroomsEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long chatroomId;

    // POST 테이블과 연결
    @ManyToOne
    @JoinColumn(name = "post_id", nullable = false)
    private PostEntity postEntity;

//  // CHATROOM_MEMBERS 테이블과 연결
//    @OneToMany(mappedBy = "chatroomsEntity", cascade = CascadeType.ALL, orphanRemoval = true)
//    private List<ChatroomMembersEntity> chatroomMembersEntityList = new ArrayList<>();

//  // CHAT_MESSAGE 테이블과 연결
//    @OneToMany(mappedBy = "chatroomsEntity", cascade = CascadeType.ALL, orphanRemoval = true)
//    private List<ChatMessageEntity> chatMessageEntityList = new ArrayList<>();

    @Column(name = "chat_name")
    private String chatName;

    @Column(name = "user_count")
    private int userCount;

    @Column(name = "created_date")
    @CreatedDate
    private LocalDateTime createdDate;
}
