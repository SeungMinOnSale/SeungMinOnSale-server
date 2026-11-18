package com.yaho.seungminonsale.worker.service;

import com.yaho.seungminonsale.common.response.PageResponse;
import com.yaho.seungminonsale.worker.dto.WorkerResponse;
import com.yaho.seungminonsale.worker.dto.WorkerUpdateRequest;

public interface WorkerService {

    PageResponse<WorkerResponse> getWorkers(String skills, Boolean available, int page, int size);

    WorkerResponse getWorker(Long workerId);

    WorkerResponse updateWorker(Long workerId, WorkerUpdateRequest request);
}
