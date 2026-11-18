package com.yaho.seungminonsale.projectresult.service;

import com.yaho.seungminonsale.projectresult.dto.ProjectResultCreateRequest;
import com.yaho.seungminonsale.projectresult.dto.ProjectResultResponse;

import java.util.List;

public interface ProjectResultService {

    ProjectResultResponse createProjectResult(Long userId, ProjectResultCreateRequest request);

    List<ProjectResultResponse> getProjectResults(Long projectId);
}
