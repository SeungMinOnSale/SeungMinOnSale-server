package com.yaho.seungminonsale.project.service;

import com.yaho.seungminonsale.project.dto.ProjectCreateRequest;
import com.yaho.seungminonsale.project.dto.ProjectResponse;
import com.yaho.seungminonsale.user.entity.User;

public interface ProjectService {

    ProjectResponse createProject(
            ProjectCreateRequest request,
            Long userId
    );
}