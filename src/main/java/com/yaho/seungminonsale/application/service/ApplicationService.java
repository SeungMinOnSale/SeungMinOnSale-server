package com.yaho.seungminonsale.application.service;

import com.yaho.seungminonsale.application.dto.ApplicationDecisionRequest;
import com.yaho.seungminonsale.teammembership.dto.TeamMembershipResponse;

import java.util.Optional;

public interface ApplicationService {

    TeamMembershipResponse decideApplication(Long userId, Long applicationId, ApplicationDecisionRequest request);
}
