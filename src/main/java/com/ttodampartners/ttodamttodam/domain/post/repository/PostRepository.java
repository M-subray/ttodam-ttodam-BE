package com.ttodampartners.ttodamttodam.domain.post.repository;

import com.ttodampartners.ttodamttodam.domain.post.entity.PostEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<PostEntity, Long> {
}
