package com.yaho.seungminonsale.activitylog.service;

import com.yaho.seungminonsale.activitylog.domain.entity.ActivityLog;
import com.yaho.seungminonsale.activitylog.dto.ActivityLogCreateRequest;
import com.yaho.seungminonsale.activitylog.dto.ActivityLogResponse;
import com.yaho.seungminonsale.activitylog.repository.ActivityLogRepository;
import com.yaho.seungminonsale.exception.MembershipNotFoundException;
import com.yaho.seungminonsale.exception.NotMembershipOwnerException;
import com.yaho.seungminonsale.exception.ProjectNotOngoingException;
import com.yaho.seungminonsale.teammembership.domain.entity.TeamMembership;
import com.yaho.seungminonsale.teammembership.repository.TeamMembershipRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ActivityLogServiceImpl implements ActivityLogService {

    private final ActivityLogRepository activityLogRepository;
    private final TeamMembershipRepository teamMembershipRepository;

    @Override
    @Transactional
    public ActivityLogResponse createActivityLog(Long userId, Long membershipId, ActivityLogCreateRequest request) {
        TeamMembership membership = teamMembershipRepository.findById(membershipId)
                .orElseThrow(MembershipNotFoundException::new);

        if (!membership.getWorker().getUser().getId().equals(userId))
            throw new NotMembershipOwnerException();

        if (!membership.getProject().isOngoing())
            throw new ProjectNotOngoingException();

        ActivityLog activityLog = activityLogRepository.save(
                ActivityLog.create(membership, request.content(), request.activityDate()));

        return ActivityLogResponse.from(activityLog);
    }
}
