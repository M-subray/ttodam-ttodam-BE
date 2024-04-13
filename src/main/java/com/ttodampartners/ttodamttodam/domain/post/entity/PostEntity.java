package com.ttodampartners.ttodamttodam.domain.post.entity;

import com.ttodampartners.ttodamttodam.domain.user.entity.UserEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@EntityListeners(AuditingEntityListener.class)
@Entity(name = "POST")
public class PostEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "post_id", nullable = false)
    private Long postId;

    // USER 테이블과 연결
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private UserEntity userEntity;

    // PRODUCT 테이블과 연결
//    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL)
//    private List<ProductEntity> products = new ArrayList<>();

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Category category;

    @Column(nullable = false)
    private Integer participants;

    /*
        편의를 위해
        place, pLocation, deadline, content, postImgUrl은
        일단 nullable=true 설정
     */
    @Column
    private String place;

    @Column(name = "p_location_x")
    private Double pLocationX;

    @Column(name = "p_location_y")
    private Double pLocationY;

    @Column
    private LocalDateTime deadline;

    @Lob
    @Column(columnDefinition = "TEXT")
    private String content;

    @Column(nullable = false)
    private String status;

    @Column(name = "product_img_url")
    private String productImgUrl;

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

}
