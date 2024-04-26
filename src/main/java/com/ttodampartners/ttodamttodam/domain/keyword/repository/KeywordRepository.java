package com.ttodampartners.ttodamttodam.domain.keyword.repository;

import com.ttodampartners.ttodamttodam.domain.keyword.entity.KeywordEntity;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface KeywordRepository extends JpaRepository<KeywordEntity, Long> {
  boolean existsByKeywordNameAndUserId(String keywordName, Long userId);
  List<KeywordEntity> findAllByUserId(Long userId);
  List<KeywordEntity> findAllByKeywordName(String keywordName);
}
