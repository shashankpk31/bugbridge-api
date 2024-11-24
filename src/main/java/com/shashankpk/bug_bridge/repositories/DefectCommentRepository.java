package com.shashankpk.bug_bridge.repositories;

import com.shashankpk.bug_bridge.models.DefectComment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DefectCommentRepository extends JpaRepository<DefectComment, Long> {
    List<DefectComment> findByDefectId(Long defectId);
}
