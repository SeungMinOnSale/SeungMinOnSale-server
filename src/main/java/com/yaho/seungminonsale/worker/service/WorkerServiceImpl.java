package com.yaho.seungminonsale.worker.service;

import com.yaho.seungminonsale.common.exception.BusinessException;
import com.yaho.seungminonsale.common.exception.ErrorCode;
import com.yaho.seungminonsale.common.response.PageResponse;
import com.yaho.seungminonsale.worker.dto.WorkerResponse;
import com.yaho.seungminonsale.worker.dto.WorkerUpdateRequest;
import com.yaho.seungminonsale.worker.entity.Worker;
import com.yaho.seungminonsale.worker.repository.WorkerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class WorkerServiceImpl implements WorkerService {

    private static final int MAX_PAGE_SIZE = 100;

    private final WorkerRepository workerRepository;

    @Override
    public PageResponse<WorkerResponse> getWorkers(String skills, Boolean available, int page, int size) {
        if (page < 0 || size < 1 || size > MAX_PAGE_SIZE) {
            throw new BusinessException(ErrorCode.INVALID_INPUT, "page는 0 이상, size는 1~100 사이로 입력해야 합니다.");
        }

        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "id"));
        boolean hasSkills = StringUtils.hasText(skills);

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

        return PageResponse.from(workers.map(WorkerResponse::from));
    }

    @Override
    public WorkerResponse getWorker(Long workerId) {
        return WorkerResponse.from(findWorker(workerId));
    }

    @Override
    @Transactional
    public WorkerResponse updateWorker(Long workerId, WorkerUpdateRequest request) {
        if (!StringUtils.hasText(request.name()) || request.available() == null) {
            throw new BusinessException(ErrorCode.INVALID_INPUT, "이름과 참여 가능 여부는 필수입니다.");
        }

        Worker worker = findWorker(workerId);
        worker.update(request.name(), request.phone(), request.skills(), request.available());
        return WorkerResponse.from(worker);
    }

    private Worker findWorker(Long workerId) {
        return workerRepository.findById(workerId)
                .orElseThrow(() -> new BusinessException(ErrorCode.WORKER_NOT_FOUND));
    }
}
