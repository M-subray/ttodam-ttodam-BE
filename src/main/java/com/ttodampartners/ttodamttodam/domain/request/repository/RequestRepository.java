package com.ttodampartners.ttodamttodam.domain.request.repository;

import com.ttodampartners.ttodamttodam.domain.request.entity.RequestEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface RequestRepository extends JpaRepository<RequestEntity, Long> {

}
