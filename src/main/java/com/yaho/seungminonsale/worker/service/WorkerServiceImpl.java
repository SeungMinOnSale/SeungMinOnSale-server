package com.yaho.seungminonsale.worker.service;

import com.yaho.seungminonsale.exception.WorkerNotFoundException;
import com.yaho.seungminonsale.worker.dto.request.WorkerUpdateRequest;
import com.yaho.seungminonsale.worker.dto.response.WorkerResponse;
import com.yaho.seungminonsale.worker.entity.Worker;
import com.yaho.seungminonsale.worker.repository.WorkerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class WorkerServiceImpl implements WorkerService {

    private final WorkerRepository workerRepository;

    @Override
    public Page<WorkerResponse> findAll(String skills, Boolean available, Pageable pageable) {
        boolean hasSkills = skills != null && !skills.isBlank();

        Page<Worker> workers;
        if (hasSkills && available != null) {
            workers = workerRepository.findBySkillsContainingIgnoreCaseAndAvailable(skills, available, pageable);
        } else if (hasSkills) {
            workers = workerRepository.findBySkillsContainingIgnoreCase(skills, pageable);
        } else if (available != null) {
            workers = workerRepository.findByAvailable(available, pageable);
        } else {
            workers = workerRepository.findAll(pageable);
        }
        return workers.map(WorkerResponse::from);
    }

    @Override
    public WorkerResponse findById(Long id) {
        return WorkerResponse.from(getWorker(id));
    }

    @Override
    @Transactional
    public WorkerResponse update(Long id, WorkerUpdateRequest request) {
        Worker worker = getWorker(id);
        worker.update(request.getName(), request.getPhone(), request.getSkills(), request.getAvailable());
        // updatedAt은 flush 시점에 바뀌므로 응답 전에 flush
        workerRepository.flush();
        return WorkerResponse.from(worker);
    }

    private Worker getWorker(Long id) {
        return workerRepository.findById(id)
                .orElseThrow(WorkerNotFoundException::new);
    }
}
