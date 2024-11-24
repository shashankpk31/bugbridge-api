package com.shashankpk.bug_bridge.repositories;

import com.shashankpk.bug_bridge.models.Defect;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DefectRepository extends JpaRepository<Defect, Long> {
}

