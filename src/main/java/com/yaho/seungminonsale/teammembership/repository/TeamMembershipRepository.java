package com.yaho.seungminonsale.teammembership.repository;

import com.yaho.seungminonsale.teammembership.domain.entity.TeamMembership;
import com.yaho.seungminonsale.teammembership.domain.enums.MembershipStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TeamMembershipRepository extends JpaRepository<TeamMembership, Long> {

    boolean existsByProject_IdAndWorker_Id(Long projectId, Long workerId);

    @Query("""
        select t
        from TeamMembership t
        join fetch t.project
        join fetch t.worker
        order by t.id
    """)
    List<TeamMembership> findAllWithProjectAndWorker();

    @Query("""
        select t
        from TeamMembership t
        join fetch t.project
        join fetch t.worker
        where t.project.id = :projectId
        order by t.id
    """)
    List<TeamMembership> findAllByProject_Id(@Param("projectId") Long projectId);

    @Query("""
        select t
        from TeamMembership t
        join fetch t.project
        join fetch t.worker
        where t.worker.id = :workerId
        order by t.id
    """)
    List<TeamMembership> findAllByWorker_Id(@Param("workerId") Long workerId);

    List<TeamMembership> findAllByProject_IdAndStatus(Long projectId, MembershipStatus status);
}
