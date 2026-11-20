package com.yaho.seungminonsale.recruitment.service;

import com.yaho.seungminonsale.project.entity.Project;
import com.yaho.seungminonsale.project.repository.ProjectRepository;
import com.yaho.seungminonsale.recruitment.dto.RecruitmentPostCreateRequest;
import com.yaho.seungminonsale.recruitment.dto.RecruitmentPostResponse;
import com.yaho.seungminonsale.recruitment.entity.RecruitmentPost;
import com.yaho.seungminonsale.recruitment.repository.RecruitmentPostRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@AllArgsConstructor
public class RecruitmentPostServiceImpl implements RecruitmentPostService {

    private final RecruitmentPostRepository recruitmentPostRepository;
    private final ProjectRepository projectRepository;

    @Override
    @Transactional
    public RecruitmentPostResponse createRecruitmentPost(
            RecruitmentPostCreateRequest request,
            Long projectId
    ) {
        if (request == null ||
                request.title() == null ||
                request.title().isBlank() ||
                request.requiredSkills() == null ||
                request.requiredSkills().isBlank()) {
            throw new IllegalArgumentException(
                    "모집글 제목과 필요 기술은 필수입니다."
            );
        }

        if (request.capacity() == null || request.capacity() <= 0) {
            throw new IllegalArgumentException(
                    "모집 인원은 1명 이상이어야 합니다."
            );
        }

        if (projectId == null) {
            throw new IllegalArgumentException(
                    "프로젝트 ID는 필수입니다."
            );
        }

        Project project = projectRepository.findById(projectId)
                .orElseThrow(() -> new IllegalArgumentException(
                        "존재하지 않는 프로젝트입니다."
                ));

        RecruitmentPost recruitmentPost = RecruitmentPost.createDefault(
                project,
                request.title(),
                request.position(),
                request.requiredSkills(),
                request.capacity()
        );

        RecruitmentPost savedPost =
                recruitmentPostRepository.save(recruitmentPost);

        return new RecruitmentPostResponse(
                savedPost.getId(),
                savedPost.getProject().getId(),
                savedPost.getTitle(),
                savedPost.getPosition(),
                savedPost.getRequiredSkills(),
                savedPost.getCapacity(),
                savedPost.getStatus()
        );
    }
}