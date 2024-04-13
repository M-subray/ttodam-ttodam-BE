package com.ttodampartners.ttodamttodam.domain.user.entity;

import com.ttodampartners.ttodamttodam.domain.post.entity.PostEntity;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@Entity(name = "USER")
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id", nullable = false)
    private Long userId;

    // POST 테이블과 연결
    @OneToMany(mappedBy = "userEntity", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<PostEntity> postEntityList;

    // CHATROOM_MEMBER 테이블과 연결
//    @OneToMany(mappedBy = "userEntity", cascade = CascadeType.ALL, orphanRemoval = true)
//    private List<ChatroomMembersEntity> chatroomMembersEntityList = new ArrayList<>();

    // CHAT_MESSAGES 테이블과 연결
//    @OneToMany(mappedBy = "userEntity", cascade = CascadeType.ALL, orphanRemoval = true)
//    private List<ChatMessageEntity> chatMessageEntityList = new ArrayList<>();

    @Column(name = "nickname", nullable = false)
    private String nickname;

    @Column(name = "profile_img_url")
    private String profileImgUrl;
}
