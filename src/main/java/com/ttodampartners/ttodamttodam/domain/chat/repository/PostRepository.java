package com.ttodampartners.ttodamttodam.domain.chat.repository;

import com.ttodampartners.ttodamttodam.domain.chat.entity.PostEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PostRepository extends JpaRepository<PostEntity, Long> {
    Optional<PostEntity> findByPostId(Long id);
}
