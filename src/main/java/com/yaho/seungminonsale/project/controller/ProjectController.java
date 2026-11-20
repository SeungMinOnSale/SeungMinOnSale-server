package com.yaho.seungminonsale.project.controller;

import com.yaho.seungminonsale.project.dto.ProjectCreateRequest;
import com.yaho.seungminonsale.project.dto.ProjectResponse;
import com.yaho.seungminonsale.project.service.ProjectService;
import com.yaho.seungminonsale.user.entity.User;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/projects")
@AllArgsConstructor
public class ProjectController {

    private final ProjectService projectService;

    @PostMapping
    public ResponseEntity<ProjectResponse> createProject(
            @RequestBody ProjectCreateRequest request,
            @RequestAttribute("currentUser") User owner
    ) {
        ProjectResponse response =
                projectService.createProject(request, owner);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(response);
    }
}