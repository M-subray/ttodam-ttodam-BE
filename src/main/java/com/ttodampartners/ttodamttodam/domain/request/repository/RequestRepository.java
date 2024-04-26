package com.ttodampartners.ttodamttodam.domain.request.repository;

import com.ttodampartners.ttodamttodam.domain.request.entity.RequestEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface RequestRepository extends JpaRepository<RequestEntity, Long> {

    List<RequestEntity> findAllByPost_PostId(Long postId);
    List<RequestEntity> findAllByRequestUser_Id(Long RequestUserId);
}
