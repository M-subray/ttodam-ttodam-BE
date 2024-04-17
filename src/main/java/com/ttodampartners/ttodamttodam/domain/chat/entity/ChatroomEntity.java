package com.ttodampartners.ttodamttodam.domain.chat.entity;

import com.ttodampartners.ttodamttodam.domain.post.entity.PostEntity;
import com.ttodampartners.ttodamttodam.domain.user.dto.model.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@AuditOverride(forClass = BaseEntity.class)
@Entity(name = "chatroom")
public class ChatroomEntity extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long chatroomId;

    // POST 테이블과 연결
    @ManyToOne
    @JoinColumn(name = "post_id", nullable = false)
    private PostEntity postEntity;

  // CHATROOM_MEMBER 테이블과 연결
    @OneToMany(mappedBy = "chatroomEntity", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ChatroomMemberEntity> chatroomMembersEntityList;

    @Column(name = "chat_name")
    private String chatName;

    @Column(name = "user_count")
    private int userCount;

}
