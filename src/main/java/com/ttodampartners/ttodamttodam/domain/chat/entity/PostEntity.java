package com.ttodampartners.ttodamttodam.domain.chat.entity;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor
@EntityListeners(AuditingEntityListener.class)
@Entity(name = "post")
public class PostEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "post_id", nullable = false)
    private Long postId;

    // USER 테이블과 연결
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private UserEntity userEntity;

    // CHATROOMS 테이블과 연결
    @OneToMany(mappedBy = "postEntity", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ChatroomsEntity> chatroomsEntityList = new ArrayList<>();

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private Integer participants;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Category category;

    @Column(name = "create_at", nullable = false)
    @CreatedDate
    private LocalDateTime createdAt;

    @Column(name = "update_at", nullable = false)
    @LastModifiedDate
    private LocalDateTime updatedAt;

    @Getter
    public enum Category {
        DAILY("생활용품"),
        KITCHEN("주방용품"),
        FOOD("식품"),
        PET("반려동물용품"),
        CLOTHING("의류/잡화"),
        HEALTH("헬스/건강식품"),
        OFFICE("오피스/문구"),
        OTHER("기타");

        private final String label;
        Category(String label) {
            this.label = label;
        }
    }

    public void createChatroom(String title, int userCount) {
        this.chatroomsEntityList.add(ChatroomsEntity.builder().postEntity(this).chatName(title).userCount(userCount).build());
    }

}
