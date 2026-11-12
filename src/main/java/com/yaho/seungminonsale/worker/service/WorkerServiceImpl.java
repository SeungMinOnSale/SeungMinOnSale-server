package com.yaho.seungminonsale.worker.service;

import com.yaho.seungminonsale.worker.dto.WorkerResponse;
import com.yaho.seungminonsale.worker.dto.WorkerUpdateRequest;
import com.yaho.seungminonsale.worker.entity.Worker;
import com.yaho.seungminonsale.worker.repository.WorkerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
@RequiredArgsConstructor
public class WorkerServiceImpl implements WorkerService {

    private final WorkerRepository workerRepository;

    @Override
    public List<WorkerResponse> getWorkers() {
        return workerRepository.findAll().stream()
                .map(WorkerResponse::from)
                .toList();
    }

    @Override
    public WorkerResponse getWorker(Long workerId) {
        return WorkerResponse.from(findWorker(workerId));
    }

    @Override
    public WorkerResponse updateWorker(Long workerId, WorkerUpdateRequest request) {
        if (isBlankValue(request.name()) || isBlankValue(request.phone())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "이름과 연락처는 공백일 수 없습니다.");
        }

        Worker worker = findWorker(workerId);
        worker.update(request.name(), request.phone(), request.skills(), request.available());
        return WorkerResponse.from(workerRepository.save(worker));
    }

    private Worker findWorker(Long workerId) {
        return workerRepository.findById(workerId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "근로자를 찾을 수 없습니다."));
    }

    private boolean isBlankValue(String value) {
        return value != null && value.isBlank();
    }
}