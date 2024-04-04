package com.ttodampartners.ttodamttodam.domain.post.entity;

// import com.ttodampartners.ttodamttodam.domain.user.entity.UserEntity;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

//@Audited
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
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

    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "participants", nullable = false)
    private Integer participants;

    @Column(name = "place", nullable = false)
    private String place;

    @Column(name = "p_location_x", nullable = false)
    private Double pLocationX;

    @Column(name = "p_location_y", nullable = false)
    private Double pLocationY;

    @Column(name = "deadline", nullable = false)
    private LocalDateTime deadline;

    @Column(name = "category", nullable = false)
    private String category;

    @Lob
    @Column(name = "content", columnDefinition = "TEXT", nullable = false)
    private String content;

    @Column(name = "create_at", nullable = false)
    @CreatedDate
    private LocalDateTime createdAt;

    @Column(name = "update_at", nullable = false)
    @LastModifiedDate
    private LocalDateTime updatedAt;

    @Column(name = "product_name", nullable = false)
    private String productName;

    @Column(name = "purchase_link", nullable = false)
    private String purchaseLink;


    @Column(name = "price", nullable = false)
    private Long price;


    @Column(name = "product_img_url", nullable = false)
    private String productImgUrl;

}
