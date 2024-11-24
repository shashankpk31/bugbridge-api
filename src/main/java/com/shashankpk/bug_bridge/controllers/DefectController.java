package com.shashankpk.bug_bridge.controllers;


import com.shashankpk.bug_bridge.dto.CommentDTO;
import com.shashankpk.bug_bridge.dto.DefectDTO;
import com.shashankpk.bug_bridge.dto.DefectStatusUpdateDTO;
import com.shashankpk.bug_bridge.models.DefectComment;
import com.shashankpk.bug_bridge.models.DefectHistory;
import com.shashankpk.bug_bridge.security.JwtTokenUtil;
import com.shashankpk.bug_bridge.services.DefectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.shashankpk.bug_bridge.dto.ApiResponse;


import java.util.List;

@RestController
@RequestMapping("/api/defects")
public class DefectController {
    @Autowired
    private DefectService defectService;
    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @PostMapping
    public ResponseEntity<?> createDefect(@RequestBody DefectDTO defectDTO, @RequestHeader("Authorization") String token) {
        String creatorUsername = jwtTokenUtil.getUsernameFromToken(token.substring(7));
        defectService.createDefect(defectDTO, creatorUsername);
        return ResponseEntity.ok(new ApiResponse("Defect created successfully"));
    }

    @GetMapping
    public ResponseEntity<List<DefectDTO>> getAllDefects() {
        return ResponseEntity.ok(defectService.getAllDefects());
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateDefectStatus(@PathVariable Long id, @RequestBody DefectStatusUpdateDTO statusUpdate, @RequestParam String comment, @RequestHeader("Authorization") String token) {
        String username = jwtTokenUtil.getUsernameFromToken(token.substring(7));
        defectService.updateDefectStatus(id, statusUpdate, comment, username);
        return ResponseEntity.ok(new ApiResponse("Defect status updated successfully"));
    }

    @PutMapping("/{id}/delegate")
    public ResponseEntity<?> delegateDefect(@PathVariable Long id, @RequestParam Long newOwnerId, @RequestParam String comment, @RequestHeader("Authorization") String token) {
        String username = jwtTokenUtil.getUsernameFromToken(token.substring(7));
        defectService.delegateDefect(id, newOwnerId, comment, username);
        return ResponseEntity.ok(new ApiResponse("Defect delegated successfully"));
    }

    @PostMapping("/{id}/comments")
    public ResponseEntity<?> addCommentToDefect(@PathVariable Long id, @RequestBody CommentDTO commentDTO, @RequestHeader("Authorization") String token) {
        String username = jwtTokenUtil.getUsernameFromToken(token.substring(7));
        defectService.addCommentToDefect(id, commentDTO, username);
        return ResponseEntity.ok(new ApiResponse("Comment added successfully"));
    }

    @GetMapping("/{id}/history")
    public ResponseEntity<List<DefectHistory>> getDefectHistory(@PathVariable Long id) {
        return ResponseEntity.ok(defectService.getDefectHistory(id));
    }

    @GetMapping("/{id}/comments")
    public ResponseEntity<List<DefectComment>> getDefectComments(@PathVariable Long id) {
        return ResponseEntity.ok(defectService.getDefectComments(id));
    }
}
