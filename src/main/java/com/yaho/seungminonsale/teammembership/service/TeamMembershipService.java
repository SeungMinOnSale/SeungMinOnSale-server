package com.yaho.seungminonsale.teammembership.service;

import com.yaho.seungminonsale.teammembership.dto.TeamMembershipResponse;

import java.util.List;

public interface TeamMembershipService {

    List<TeamMembershipResponse> getMemberships(Long userId, Long projectId);
}
