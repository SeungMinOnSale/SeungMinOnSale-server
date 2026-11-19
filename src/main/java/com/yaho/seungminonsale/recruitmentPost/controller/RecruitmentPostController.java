package com.yaho.seungminonsale.recruitmentPost.controller;

import com.yaho.seungminonsale.recruitmentPost.dto.RecruitmentPostCreateRequest;
import com.yaho.seungminonsale.recruitmentPost.dto.RecruitmentPostResponse;
import com.yaho.seungminonsale.recruitmentPost.service.RecruitmentPostService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/projects/{projectId}/recruitment-posts")
@RequiredArgsConstructor
public class RecruitmentPostController {

    private final RecruitmentPostService recruitmentPostService;

    @PostMapping
    public ResponseEntity<RecruitmentPostResponse> createRecruitmentPost(
            @PathVariable Long projectId,
            @RequestBody RecruitmentPostCreateRequest request
    ) {
        RecruitmentPostResponse response =
                recruitmentPostService.createRecruitmentPost(request, projectId);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(response);
    }
}