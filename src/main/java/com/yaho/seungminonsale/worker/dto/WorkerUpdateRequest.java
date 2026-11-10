package com.yaho.seungminonsale.worker.dto;

public record WorkerUpdateRequest(
        String name,
        String phone,
        String skills,
        Boolean available
) {
}