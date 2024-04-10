package com.ttodampartners.ttodamttodam.domain.post.entity;

// import com.ttodampartners.ttodamttodam.domain.user.entity.UserEntity;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

//@Audited
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EnableJpaAuditing
@Builder
@EntityListeners(AuditingEntityListener.class)
@Entity(name = "post")
public class PostEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long postId;

//    @ManyToOne
//    @JoinColumn(name = "user_id", nullable = false)
//    private UserEntity userId;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private Integer participants;

    @Column(nullable = false)
    private String place;

    @Column(name = "p_location_x", nullable = false)
    private Double pLocationX;

    @Column(name = "p_location_y", nullable = false)
    private Double pLocationY;

    @Column(nullable = false)
    private LocalDateTime deadline;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
     private Category category;

    @Lob
    @Column(columnDefinition = "TEXT", nullable = false)
    private String content;

    @Column(name = "create_at", nullable = false)
    @CreatedDate
    private LocalDateTime createdAt;

    @Column(name = "update_at", nullable = false)
    @LastModifiedDate
    private LocalDateTime updatedAt;

// 상품테이블로 빼기
    @Column(name = "product_name", nullable = false)
    private String productName;

    @Column(name = "purchase_link", nullable = false)
    private String purchaseLink;


    @Column(nullable = false)
    private Long price;


    @Column(name = "product_img_url", nullable = false)
    private String productImgUrl;

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
}
