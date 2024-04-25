package com.ttodampartners.ttodamttodam.domain.chat.repository;

import com.ttodampartners.ttodamttodam.domain.chat.entity.ChatroomEntity;
import com.ttodampartners.ttodamttodam.domain.post.entity.PostEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ChatroomRepository extends JpaRepository<ChatroomEntity, Long> {
    Optional<ChatroomEntity> findByChatroomId(Long id);
    List<ChatroomEntity> findByPostEntity(PostEntity post);
    @Query(nativeQuery = true, value = "SELECT * FROM CHATROOM WHERE post_id=? AND user_count > 2 LIMIT 1")
    ChatroomEntity findByPostEntityAndUserCountGreaterThan2(PostEntity post);
}
