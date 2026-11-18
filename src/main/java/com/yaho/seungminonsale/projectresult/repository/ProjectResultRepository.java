package com.yaho.seungminonsale.projectresult.repository;

import com.yaho.seungminonsale.projectresult.domain.entity.ProjectResult;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProjectResultRepository extends JpaRepository<ProjectResult, Long> {

    boolean existsByProject_Id(Long projectId);
}
