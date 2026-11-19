package com.yaho.seungminonsale.worker.controller;

import com.yaho.seungminonsale.worker.dto.request.WorkerPageRequest;
import com.yaho.seungminonsale.worker.dto.request.WorkerUpdateRequest;
import com.yaho.seungminonsale.worker.dto.response.WorkerPageResponse;
import com.yaho.seungminonsale.worker.dto.response.WorkerResponse;
import com.yaho.seungminonsale.worker.service.WorkerService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/workers")
@RequiredArgsConstructor
public class WorkerController {

    private final WorkerService workerService;

    @GetMapping
    public ResponseEntity<WorkerPageResponse> findAll(@Valid WorkerPageRequest request) {
        return ResponseEntity.ok(
                WorkerPageResponse.from(workerService.findAll(
                        request.getSkills(), request.getAvailable(), request.toPageable()))
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<WorkerResponse> findById(@PathVariable Long id) {
        return ResponseEntity.ok(workerService.findById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<WorkerResponse> update(
            @PathVariable Long id,
            @RequestBody @Valid WorkerUpdateRequest request
    ) {
        return ResponseEntity.ok(workerService.update(id, request));
    }
}