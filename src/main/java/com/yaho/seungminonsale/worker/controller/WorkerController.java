package com.yaho.seungminonsale.worker.controller;

import com.yaho.seungminonsale.common.response.ApiResponse;
import com.yaho.seungminonsale.common.response.PageResponse;
import com.yaho.seungminonsale.worker.dto.WorkerResponse;
import com.yaho.seungminonsale.worker.dto.WorkerUpdateRequest;
import com.yaho.seungminonsale.worker.service.WorkerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/workers")
@RequiredArgsConstructor
public class WorkerController {

    private final WorkerService workerService;

    @GetMapping
    public ResponseEntity<ApiResponse<PageResponse<WorkerResponse>>> getWorkers(
            @RequestParam(required = false) String skills,
            @RequestParam(required = false) Boolean available,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        return ResponseEntity.ok(ApiResponse.success("학생 목록 조회에 성공했습니다.",
                workerService.getWorkers(skills, available, page, size)));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<WorkerResponse>> getWorker(@PathVariable Long id) {
        return ResponseEntity.ok(ApiResponse.success("학생 상세 조회에 성공했습니다.", workerService.getWorker(id)));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<WorkerResponse>> updateWorker(@PathVariable Long id,
                                                                    @RequestBody WorkerUpdateRequest request) {
        return ResponseEntity.ok(ApiResponse.success("학생 프로필이 수정되었습니다.",
                workerService.updateWorker(id, request)));
    }
}
