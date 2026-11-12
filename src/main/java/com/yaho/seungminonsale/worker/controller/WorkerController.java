package com.yaho.seungminonsale.worker.controller;

import com.yaho.seungminonsale.worker.dto.WorkerResponse;
import com.yaho.seungminonsale.worker.dto.WorkerUpdateRequest;
import com.yaho.seungminonsale.worker.service.WorkerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/workers")
@RequiredArgsConstructor
public class WorkerController {

    private final WorkerService workerService;

    @GetMapping
    public ResponseEntity<List<WorkerResponse>> getWorkers() {
        return ResponseEntity.ok(workerService.getWorkers());
    }

    @GetMapping("/{id}")
    public ResponseEntity<WorkerResponse> getWorker(@PathVariable Long id) {
        return ResponseEntity.ok(workerService.getWorker(id));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<WorkerResponse> updateWorker(@PathVariable Long id, @RequestBody WorkerUpdateRequest request) {
        return ResponseEntity.ok(workerService.updateWorker(id, request));
    }
}