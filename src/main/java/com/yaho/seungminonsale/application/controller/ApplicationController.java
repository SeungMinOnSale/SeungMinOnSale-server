package com.yaho.seungminonsale.application.controller;

import com.yaho.seungminonsale.application.dto.ApplicationDecisionRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/applications")
public class ApplicationController {
    @PutMapping("/{id}/decision")
    public ResponseEntity<Void> decideApplication(
            @PathVariable Long id,
            @RequestParam Long userId,
            @RequestBody ApplicationDecisionRequest request
    ) {
        return null;
    }
}
