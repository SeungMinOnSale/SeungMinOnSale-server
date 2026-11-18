package com.yaho.seungminonsale.project.repository;

import com.yaho.seungminonsale.project.entity.Project;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface ProjectRepository extends JpaRepository<Project, Long> {

    List<Project> findAllByOwner_Id(Long ownerId);
}