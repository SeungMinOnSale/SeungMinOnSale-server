package com.yaho.seungminonsale.activitylog.repository;

import com.yaho.seungminonsale.activitylog.domain.entity.ActivityLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ActivityLogRepository extends JpaRepository<ActivityLog, Long> {
}
