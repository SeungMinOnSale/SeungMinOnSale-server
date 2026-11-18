package com.yaho.seungminonsale.worker.dto.response;

import com.yaho.seungminonsale.worker.entity.Worker;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class WorkerResponse {
    private Long id;
    private String name;
    private String skills;
    private boolean available;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public static WorkerResponse from(Worker worker) {
        return WorkerResponse.builder()
                .id(worker.getId())
                .name(worker.getName())
                .skills(worker.getSkills())
                .available(worker.isAvailable())
                .createdAt(worker.getCreatedAt())
                .updatedAt(worker.getUpdatedAt())
                .build();
    }
}
