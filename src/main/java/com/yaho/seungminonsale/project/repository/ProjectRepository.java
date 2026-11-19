package com.yaho.seungminonsale.project.repository;

import com.yaho.seungminonsale.project.entity.Project;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProjectRepository extends JpaRepository<Project, Long> {
}
