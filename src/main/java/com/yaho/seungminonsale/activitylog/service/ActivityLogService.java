package com.yaho.seungminonsale.activitylog.service;

import com.yaho.seungminonsale.activitylog.dto.ActivityLogCreateRequest;
import com.yaho.seungminonsale.activitylog.dto.ActivityLogResponse;

public interface ActivityLogService {

    ActivityLogResponse createActivityLog(Long userId, Long membershipId, ActivityLogCreateRequest request);
}
