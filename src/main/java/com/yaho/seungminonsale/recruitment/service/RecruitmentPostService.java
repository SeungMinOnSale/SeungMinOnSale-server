package com.yaho.seungminonsale.recruitment.service;

import com.yaho.seungminonsale.recruitment.dto.RecruitmentPostCreateRequest;
import com.yaho.seungminonsale.recruitment.dto.RecruitmentPostResponse;

public interface RecruitmentPostService {

    RecruitmentPostResponse createRecruitmentPost(
            RecruitmentPostCreateRequest request,
            Long projectId
    );
}