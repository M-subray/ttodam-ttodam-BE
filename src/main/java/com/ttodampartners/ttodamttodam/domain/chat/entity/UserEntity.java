package com.ttodampartners.ttodamttodam.domain.chat.entity;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@Getter
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
