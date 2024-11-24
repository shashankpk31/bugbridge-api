package com.shashankpk.bug_bridge.controllers;

import com.shashankpk.bug_bridge.dto.EnvironmentDTO;
import com.shashankpk.bug_bridge.dto.ApiResponse;
import com.shashankpk.bug_bridge.security.JwtTokenUtil;
import com.shashankpk.bug_bridge.services.EnvironmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/environments")
public class EnvironmentController {
    @Autowired
    private JwtTokenUtil jwtTokenUtil;
    @Autowired
    private EnvironmentService environmentService;

    @PostMapping
    public ResponseEntity<?> createEnvironment(@RequestBody EnvironmentDTO environmentDTO, @RequestHeader("Authorization") String token) {
        String username = jwtTokenUtil.getUsernameFromToken(token.substring(7));
        environmentService.createEnvironment(environmentDTO,username);
        return ResponseEntity.ok(new ApiResponse("Environment created successfully"));
    }

    @GetMapping
    public ResponseEntity<List<EnvironmentDTO>> getAllEnvironments() {
        return ResponseEntity.ok(environmentService.getAllEnvironments());
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateEnvironment(@PathVariable Long id, @RequestBody EnvironmentDTO environmentDTO) {
        environmentService.updateEnvironment(id, environmentDTO);
        return ResponseEntity.ok(new ApiResponse("Environment updated successfully"));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteEnvironment(@PathVariable Long id) {
        environmentService.deleteEnvironment(id);
        return ResponseEntity.ok(new ApiResponse("Environment deleted successfully"));
    }
}

