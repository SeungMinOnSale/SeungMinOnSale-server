package com.yaho.seungminonsale.worker.dto.response;

import lombok.Builder;
import lombok.Getter;
import org.springframework.data.domain.Page;

import java.util.List;

@Getter
@Builder
public class WorkerPageResponse {
    private List<WorkerResponse> content;
    private int page;
    private int size;
    private long totalElements;
    private int totalPages;

    public static WorkerPageResponse from(Page<WorkerResponse> page) {
        return WorkerPageResponse.builder()
                .content(page.getContent())
                .page(page.getNumber() + 1)
                .size(page.getSize())
                .totalElements(page.getTotalElements())
                .totalPages(page.getTotalPages())
                .build();
    }
}
