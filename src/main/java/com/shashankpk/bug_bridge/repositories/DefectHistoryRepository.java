package com.shashankpk.bug_bridge.repositories;

import com.shashankpk.bug_bridge.models.DefectHistory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DefectHistoryRepository extends JpaRepository<DefectHistory, Long> {
    List<DefectHistory> findByDefectId(Long defectId);
}
