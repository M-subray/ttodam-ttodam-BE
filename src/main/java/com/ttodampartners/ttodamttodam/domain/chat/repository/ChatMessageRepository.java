package com.ttodampartners.ttodamttodam.domain.chat.repository;

import com.ttodampartners.ttodamttodam.domain.chat.entity.ChatMessageEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;

@Repository
public interface ChatMessageRepository extends JpaRepository<ChatMessageEntity, Long> {
    void deleteByCreateAtLessThanEqual(LocalDateTime localDateTime);
}
