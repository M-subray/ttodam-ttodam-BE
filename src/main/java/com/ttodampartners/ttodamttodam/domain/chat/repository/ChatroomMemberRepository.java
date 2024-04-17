package com.ttodampartners.ttodamttodam.domain.chat.repository;

import com.ttodampartners.ttodamttodam.domain.chat.entity.ChatroomMemberEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ChatroomMemberRepository extends JpaRepository<ChatroomMemberEntity, Long> {
//    Optional<ChatroomMembersEntity> findAllByUserId(Long id);
}
