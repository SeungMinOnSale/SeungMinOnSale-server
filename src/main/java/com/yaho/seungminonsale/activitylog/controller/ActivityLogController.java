package com.yaho.seungminonsale.activitylog.controller;

import com.yaho.seungminonsale.activitylog.dto.ActivityLogCreateRequest;
import com.yaho.seungminonsale.activitylog.dto.ActivityLogResponse;
import com.yaho.seungminonsale.activitylog.service.ActivityLogService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/memberships")
public class ActivityLogController {

    private final ActivityLogService activityLogService;

    @PostMapping("/{id}/activities")
    public ResponseEntity<ActivityLogResponse> createActivityLog(
            @PathVariable Long id,
            @RequestParam Long userId,
            @RequestBody @Valid ActivityLogCreateRequest request
    ) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(activityLogService.createActivityLog(userId, id, request));
    }
}
