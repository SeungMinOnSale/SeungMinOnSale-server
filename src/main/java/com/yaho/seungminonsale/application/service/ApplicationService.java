package com.yaho.seungminonsale.application.service;

import com.yaho.seungminonsale.application.dto.ApplicationDecisionRequest;
import com.yaho.seungminonsale.application.dto.ApplicationDecisionResponse;

public interface ApplicationService {
    ApplicationDecisionResponse decideApplication(Long id, Long userId, ApplicationDecisionRequest request);
}
