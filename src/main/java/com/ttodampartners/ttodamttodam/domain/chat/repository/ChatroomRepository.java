package com.ttodampartners.ttodamttodam.domain.chat.repository;

import com.ttodampartners.ttodamttodam.domain.chat.entity.ChatroomEntity;
import com.ttodampartners.ttodamttodam.domain.post.entity.PostEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ChatroomRepository extends JpaRepository<ChatroomEntity, Long> {
    Optional<ChatroomEntity> findByChatroomId(Long id);
    List<ChatroomEntity> findByPostEntity(PostEntity post);
}
