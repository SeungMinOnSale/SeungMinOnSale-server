package com.yaho.seungminonsale.teammembership.repository;

import com.yaho.seungminonsale.teammembership.domain.entity.TeamMembership;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TeamMembershipRepository extends JpaRepository<TeamMembership, Long> {
    boolean existsByProject_IdAndWorker_Id(Long projectId, Long workerId);
}
