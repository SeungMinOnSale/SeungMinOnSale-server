package com.yaho.seungminonsale.worker.dto;

import com.yaho.seungminonsale.worker.entity.Worker;

import java.time.LocalDateTime;

public record WorkerResponse(
        Long workerId,
        Long userId,
        String name,
        String phone,
        String skills,
        boolean available,
        LocalDateTime createdAt
) {

    public static WorkerResponse from(Worker worker) {
        return new WorkerResponse(
                worker.getId(),
                worker.getUser().getId(),
                worker.getName(),
                worker.getPhone(),
                worker.getSkills(),
                worker.isAvailable(),
                worker.getCreatedAt()
        );
    }
}