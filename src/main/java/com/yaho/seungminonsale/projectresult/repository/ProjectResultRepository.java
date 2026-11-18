package com.yaho.seungminonsale.projectresult.repository;

import com.yaho.seungminonsale.projectresult.domain.entity.ProjectResult;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProjectResultRepository extends JpaRepository<ProjectResult, Long> {

    boolean existsByProject_Id(Long projectId);

    @Query("""
        select p
        from ProjectResult p
        join fetch p.project
        order by p.id
    """)
    List<ProjectResult> findAllWithProject();

    @Query("""
        select p
        from ProjectResult p
        join fetch p.project
        where p.project.id = :projectId
    """)
    Optional<ProjectResult> findByProject_Id(@Param("projectId") Long projectId);
}
