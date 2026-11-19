package com.yaho.seungminonsale.recruitmentPost.service;

import com.yaho.seungminonsale.recruitmentPost.dto.RecruitmentPostCreateRequest;
import com.yaho.seungminonsale.recruitmentPost.dto.RecruitmentPostResponse;

public interface RecruitmentPostService {

    RecruitmentPostResponse createRecruitmentPost(
            RecruitmentPostCreateRequest request,
            Long projectId
    );
}