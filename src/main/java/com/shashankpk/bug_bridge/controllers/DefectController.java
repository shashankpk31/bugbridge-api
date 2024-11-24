package com.shashankpk.bug_bridge.controllers;



import com.shashankpk.bug_bridge.dto.CommentDTO;
import com.shashankpk.bug_bridge.dto.DefectDTO;
import com.shashankpk.bug_bridge.dto.DefectStatusUpdateDTO;
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

    @PostMapping
    public ResponseEntity<?> createDefect(@RequestBody DefectDTO defectDTO) {
        defectService.createDefect(defectDTO);
        return ResponseEntity.ok(new ApiResponse("Defect created successfully"));
    }

    @GetMapping
    public ResponseEntity<List<DefectDTO>> getAllDefects() {
        return ResponseEntity.ok(defectService.getAllDefects());
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateDefectStatus(@PathVariable Long id, @RequestBody DefectStatusUpdateDTO statusUpdate) {
        defectService.updateDefectStatus(id, statusUpdate);
        return ResponseEntity.ok(new ApiResponse("Defect status updated successfully"));
    }

    @PostMapping("/{id}/comments")
    public ResponseEntity<?> addCommentToDefect(@PathVariable Long id, @RequestBody CommentDTO commentDTO) {
        defectService.addCommentToDefect(id, commentDTO);
        return ResponseEntity.ok(new ApiResponse("Comment added successfully"));
    }
}
