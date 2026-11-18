package com.yaho.seungminonsale.projectresult.dto;

import com.yaho.seungminonsale.projectresult.domain.entity.ProjectResult;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record ProjectResultCreateRequest(
        @NotNull(message = "프로젝트 ID 입력")
        Long projectId,

        @NotBlank(message = "결과 요약 입력")
        @Size(max = ProjectResult.SUMMARY_MAX_LENGTH, message = "결과 요약은 255자 이하로")
        String summary,

        @Size(max = ProjectResult.REPOSITORY_URL_MAX_LENGTH, message = "저장소 URL은 255자 이하로")
        String repositoryUrl
) {
}
