package com.yaho.seungminonsale.worker.service;

import com.yaho.seungminonsale.worker.dto.WorkerResponse;
import com.yaho.seungminonsale.worker.dto.WorkerUpdateRequest;

import java.util.List;

public interface WorkerService {

    List<WorkerResponse> getWorkers();

    WorkerResponse getWorker(Long workerId);

    WorkerResponse updateWorker(Long workerId, WorkerUpdateRequest request);
}