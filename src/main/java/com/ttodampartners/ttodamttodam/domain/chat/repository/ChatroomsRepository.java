package com.ttodampartners.ttodamttodam.domain.chat.repository;

import com.ttodampartners.ttodamttodam.domain.chat.entity.ChatroomsEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

public interface ChatroomsRepository extends JpaRepository<ChatroomsEntity, Long> {
    Optional<ChatroomsEntity> findByChatroomId(Long id);
}
