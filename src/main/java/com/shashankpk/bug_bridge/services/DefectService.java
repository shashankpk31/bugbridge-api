package com.shashankpk.bug_bridge.services;


import com.shashankpk.bug_bridge.dto.CommentDTO;
import com.shashankpk.bug_bridge.dto.DefectDTO;
import com.shashankpk.bug_bridge.dto.DefectStatusUpdateDTO;
import com.shashankpk.bug_bridge.models.Defect;
import com.shashankpk.bug_bridge.repositories.DefectRepository;
import com.shashankpk.bug_bridge.repositories.EnvironmentRepository;
import com.shashankpk.bug_bridge.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class DefectService {

    @Autowired
    private DefectRepository defectRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private EnvironmentRepository environmentRepository;

    public void createDefect(DefectDTO defectDTO) {
        Defect defect = new Defect();
        defect.setTitle(defectDTO.getTitle());
        defect.setDescription(defectDTO.getDescription());
        defect.setReportedBy(userRepository.findById(defectDTO.getReportedById()).orElse(null));
        defect.setEnvironment(environmentRepository.findById(defectDTO.getEnvironmentId()).orElse(null));
        defectRepository.save(defect);
    }

    public List<DefectDTO> getAllDefects() {
        List<Defect> defects = defectRepository.findAll();
        return defects.stream().map(defect -> new DefectDTO(defect.getId(), defect.getTitle(), defect.getDescription(), defect.getReportedBy().getId(), defect.getEnvironment().getId(), defect.getStatus())).collect(Collectors.toList());
    }

    public void updateDefectStatus(Long id, DefectStatusUpdateDTO statusUpdate) {
        Defect defect = defectRepository.findById(id).orElseThrow(() -> new RuntimeException("Defect not found."));
        defect.setStatus(statusUpdate.getStatus());
        defectRepository.save(defect);
    }

    public void addCommentToDefect(Long id, CommentDTO commentDTO) {
        // Implementation for adding comments to a defect
    }
}

