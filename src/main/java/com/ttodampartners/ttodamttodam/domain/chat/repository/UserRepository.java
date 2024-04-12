package com.ttodampartners.ttodamttodam.domain.chat.repository;

import com.ttodampartners.ttodamttodam.domain.chat.entity.PostEntity;
import com.ttodampartners.ttodamttodam.domain.chat.entity.UserEntity;
import org.apache.catalina.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {
    Optional<UserEntity> findByUserId(Long id);
}
