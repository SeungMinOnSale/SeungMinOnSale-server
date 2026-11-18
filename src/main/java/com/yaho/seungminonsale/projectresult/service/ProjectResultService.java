package com.yaho.seungminonsale.projectresult.service;

import com.yaho.seungminonsale.projectresult.dto.ProjectResultCreateRequest;
import com.yaho.seungminonsale.projectresult.dto.ProjectResultResponse;

public interface ProjectResultService {

    ProjectResultResponse createProjectResult(Long userId, ProjectResultCreateRequest request);
}
