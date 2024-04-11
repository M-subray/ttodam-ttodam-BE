package com.ttodampartners.ttodamttodam.domain.post.repository;

import com.ttodampartners.ttodamttodam.domain.post.entity.PostEntity;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PostRepository extends JpaRepository<PostEntity, Long> {

}
