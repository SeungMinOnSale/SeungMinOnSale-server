package com.yaho.seungminonsale.projectresult.dto;

import com.yaho.seungminonsale.projectresult.domain.entity.ProjectResult;

import java.time.LocalDateTime;

public record ProjectResultResponse(
        Long id,
        Long projectId,
        String projectName,
        String summary,
        String repositoryUrl,
        LocalDateTime createdAt
) {
    public static ProjectResultResponse from(ProjectResult projectResult) {
        return new ProjectResultResponse(
                projectResult.getId(),
                projectResult.getProject().getId(),
                projectResult.getProject().getName(),
                projectResult.getSummary(),
                projectResult.getRepositoryUrl(),
                projectResult.getCreatedAt()
        );
    }
}
