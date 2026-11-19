package com.yaho.seungminonsale.projectresult.controller;

import com.yaho.seungminonsale.projectresult.dto.ProjectResultCreateRequest;
import com.yaho.seungminonsale.projectresult.dto.ProjectResultResponse;
import com.yaho.seungminonsale.projectresult.service.ProjectResultService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/project-results")
public class ProjectResultController {

    private final ProjectResultService projectResultService;

    @PostMapping
    public ResponseEntity<ProjectResultResponse> createProjectResult(
            @RequestParam Long userId,
            @RequestBody @Valid ProjectResultCreateRequest request
    ) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(projectResultService.createProjectResult(userId, request));
    }

    @GetMapping
    public ResponseEntity<List<ProjectResultResponse>> getProjectResults(
            @RequestParam(required = false) Long projectId
    ) {
        return ResponseEntity.ok(projectResultService.getProjectResults(projectId));
    }
}
