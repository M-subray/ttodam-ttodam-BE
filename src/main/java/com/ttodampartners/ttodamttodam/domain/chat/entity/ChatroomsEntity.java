package com.ttodampartners.ttodamttodam.domain.chat.entity;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@AttributeOverride(name = "createAt", column = @Column(name = "created_date"))
@Entity(name = "chatrooms")
public class ChatroomsEntity extends BaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long chatroomId;

    // POST 테이블과 연결
    @ManyToOne
    @JoinColumn(name = "post_id", nullable = false)
    private PostEntity postEntity;

//  // CHATROOM_MEMBERS 테이블과 연결
    @OneToMany(mappedBy = "chatroomsEntity", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ChatroomMembersEntity> chatroomMembersEntityList = new ArrayList<>();

//  // CHAT_MESSAGE 테이블과 연결
    @OneToMany(mappedBy = "chatroomsEntity", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ChatMessageEntity> chatMessageEntityList = new ArrayList<>();

    @Column
    private String chatName;

    @Column
    private int userCount;
}
