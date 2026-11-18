package com.yaho.seungminonsale.application.controller;

import com.yaho.seungminonsale.application.dto.ApplicationDecisionRequest;
import com.yaho.seungminonsale.application.service.ApplicationService;
import com.yaho.seungminonsale.teammembership.dto.TeamMembershipResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/applications")
public class ApplicationController {

    private final ApplicationService applicationService;

    @PutMapping("/{id}/decision")
    public ResponseEntity<TeamMembershipResponse> decideApplication(
            @PathVariable Long id,
            @RequestParam Long userId,
            @RequestBody @Valid ApplicationDecisionRequest request
    ) {
        return ResponseEntity.ok(applicationService.decideApplication(userId, id, request));
    }
}
