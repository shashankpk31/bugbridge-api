package com.shashankpk.bug_bridge.services;


import com.shashankpk.bug_bridge.dto.CommentDTO;
import com.shashankpk.bug_bridge.dto.DefectDTO;
import com.shashankpk.bug_bridge.dto.DefectStatusUpdateDTO;
import com.shashankpk.bug_bridge.models.Defect;
import com.shashankpk.bug_bridge.models.DefectComment;
import com.shashankpk.bug_bridge.models.DefectHistory;
import com.shashankpk.bug_bridge.models.User;
import com.shashankpk.bug_bridge.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
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
    @Autowired
    private DefectHistoryRepository defectHistoryRepository;
    @Autowired
    private DefectCommentRepository defectCommentRepository;

    public void createDefect(DefectDTO defectDTO, String creatorUsername) {
        Defect defect = new Defect();
        defect.setTitle(defectDTO.getTitle());
        defect.setDescription(defectDTO.getDescription());
        defect.setReportedBy(userRepository.findById(defectDTO.getReportedById()).orElse(null));
        defect.setEnvironment(environmentRepository.findById(defectDTO.getEnvironmentId()).orElse(null));
        User creator = userRepository.findByUsername(creatorUsername).orElse(null);
        defect.setCreatedBy(creator);
        defectRepository.save(defect);
    }

    public List<DefectDTO> getAllDefects() {
        List<Defect> defects = defectRepository.findAll();
        return defects.stream().map(defect -> new DefectDTO(defect.getId(), defect.getTitle(), defect.getDescription(),  defect.getReportedBy().getId(),defect.getReportedBy().getId() ,defect.getStatus())).collect(Collectors.toList());
    }

    public void updateDefectStatus(Long id, DefectStatusUpdateDTO statusUpdate, String comment, String changerUsername) {
        Defect defect = defectRepository.findById(id).orElseThrow(() -> new RuntimeException("Defect not found."));
        String oldStatus = defect.getStatus();
        defect.setStatus(statusUpdate.getStatus());
        defect.setLastModifiedOn(new Date());
        defectRepository.save(defect);
        User changer = userRepository.findByUsername(changerUsername).orElse(null);
        DefectHistory history = new DefectHistory();
        history.setDefect(defect);
        history.setOldStatus(oldStatus);
        history.setNewStatus(statusUpdate.getStatus());
        history.setComment(comment);
        history.setChangedOn(new Date());
        history.setChangedBy(changer);
        defectHistoryRepository.save(history);
        DefectComment defectComment = new DefectComment();
        defectComment.setDefect(defect);
        defectComment.setComment(comment);
        defectComment.setCommentedOn(new Date());
        defectComment.setCommentedBy(changer);
        defectCommentRepository.save(defectComment);
    }

    public void delegateDefect(Long id, Long newOwnerId, String comment, String delegatorUsername) {
        Defect defect = defectRepository.findById(id).orElseThrow(() -> new RuntimeException("Defect not found."));
        User newOwner = userRepository.findById(newOwnerId).orElseThrow(() -> new RuntimeException("User not found."));
        defect.setCurrentOwner(newOwner);
        defect.setLastModifiedOn(new Date());
        defectRepository.save(defect);
        User delegator = userRepository.findByUsername(delegatorUsername).orElse(null);
        DefectHistory history = new DefectHistory();
        history.setDefect(defect);
        history.setOldStatus(defect.getStatus());
        history.setNewStatus("Delegated");
        history.setComment(comment);
        history.setChangedOn(new Date());
        history.setChangedBy(delegator);
        defectHistoryRepository.save(history);
        DefectComment defectComment = new DefectComment();
        defectComment.setDefect(defect);
        defectComment.setComment(comment);
        defectComment.setCommentedOn(new Date());
        defectComment.setCommentedBy(delegator);
        defectCommentRepository.save(defectComment);
    }

    public void addCommentToDefect(Long id, CommentDTO commentDTO, String commenterUsername) {
        Defect defect = defectRepository.findById(id).orElseThrow(() -> new RuntimeException("Defect not found."));
        User commenter = userRepository.findByUsername(commenterUsername).orElse(null);
        DefectComment comment = new DefectComment();
        comment.setDefect(defect);
        comment.setComment(commentDTO.getComment());
        comment.setCommentedOn(new Date());
        comment.setCommentedBy(commenter);
        defectCommentRepository.save(comment);
    }

    public List<DefectHistory> getDefectHistory(Long defectId) {
        return defectHistoryRepository.findByDefectId(defectId);
    }

    public List<DefectComment> getDefectComments(Long defectId) {
        return defectCommentRepository.findByDefectId(defectId);
    }
}

