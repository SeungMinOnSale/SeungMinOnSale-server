package com.yaho.seungminonsale.application.repository;

import com.yaho.seungminonsale.application.entity.Application;
import com.yaho.seungminonsale.application.entity.ApplicationStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ApplicationRepository extends JpaRepository<Application, Long> {

    long countByRecruitmentPost_IdAndStatus(Long recruitmentPostId, ApplicationStatus status);
}
