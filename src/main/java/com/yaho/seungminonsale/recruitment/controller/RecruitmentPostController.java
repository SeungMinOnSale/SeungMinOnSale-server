package com.yaho.seungminonsale.recruitment.controller;

import com.yaho.seungminonsale.recruitment.dto.RecruitmentPostCreateRequest;
import com.yaho.seungminonsale.recruitment.dto.RecruitmentPostResponse;
import com.yaho.seungminonsale.recruitment.service.RecruitmentPostService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/projects/{projectId}/recruitment-posts")
@AllArgsConstructor
public class RecruitmentPostController {

    private final RecruitmentPostService recruitmentPostService;

    @PostMapping
    public ResponseEntity<RecruitmentPostResponse> createRecruitmentPost(
            @PathVariable("projectId") Long projectId,
            @RequestBody RecruitmentPostCreateRequest request
    ) {
        RecruitmentPostResponse response =
                recruitmentPostService.createRecruitmentPost(request, projectId);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(response);
    }
}