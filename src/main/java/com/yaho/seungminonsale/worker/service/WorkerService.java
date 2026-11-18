package com.yaho.seungminonsale.worker.service;

import com.yaho.seungminonsale.worker.dto.request.WorkerUpdateRequest;
import com.yaho.seungminonsale.worker.dto.response.WorkerResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface WorkerService {
    Page<WorkerResponse> findAll(String skills, Boolean available, Pageable pageable);

    WorkerResponse findById(Long id);

    WorkerResponse update(Long id, WorkerUpdateRequest request);
}
