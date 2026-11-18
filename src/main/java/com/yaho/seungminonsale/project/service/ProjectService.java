
package com.yaho.seungminonsale.project.service;

import com.yaho.seungminonsale.project.dto.ProjectCreateRequest;
import com.yaho.seungminonsale.project.dto.ProjectResponse;
import com.yaho.seungminonsale.user.entity.User;

// User 클래스는 A 파트의 실제 패키지에서 import

public interface ProjectService {

    ProjectResponse createProject(
            ProjectCreateRequest request,
            User owner
    );

}
