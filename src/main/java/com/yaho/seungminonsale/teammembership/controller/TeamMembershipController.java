package com.yaho.seungminonsale.teammembership.controller;

import com.yaho.seungminonsale.teammembership.dto.TeamMembershipResponse;
import com.yaho.seungminonsale.teammembership.service.TeamMembershipService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/memberships")
public class TeamMembershipController {

    private final TeamMembershipService teamMembershipService;

    @GetMapping
    public ResponseEntity<List<TeamMembershipResponse>> getMemberships(
            @RequestParam Long userId,
            @RequestParam(required = false) Long projectId
    ) {
        return ResponseEntity.ok(teamMembershipService.getMemberships(userId, projectId));
    }
}
