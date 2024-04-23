package com.ttodampartners.ttodamttodam.domain.keyword.repository;

import com.ttodampartners.ttodamttodam.domain.keyword.entity.KeywordEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface KeywordRepository extends JpaRepository<KeywordEntity, Long> {
  boolean existsByKeywordNameAndUserId(String keywordName, Long userId);
}
