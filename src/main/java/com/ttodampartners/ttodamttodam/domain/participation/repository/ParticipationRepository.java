package com.ttodampartners.ttodamttodam.domain.participation.repository;

import com.ttodampartners.ttodamttodam.domain.participation.type.ParticipationStatus;
import com.ttodampartners.ttodamttodam.domain.participation.entity.ParticipationEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ParticipationRepository extends JpaRepository<ParticipationEntity, Long> {

    List<ParticipationEntity> findAllByPost_PostId(Long postId);
    List<ParticipationEntity> findAllByPost_PostIdAndParticipationStatus(Long postId, ParticipationStatus participationStatus);
    List<ParticipationEntity> findAllByRequestUser_Id(Long RequestUserId);
}
