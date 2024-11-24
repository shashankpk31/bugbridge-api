package com.shashankpk.bug_bridge.repositories;


import com.shashankpk.bug_bridge.models.Environment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EnvironmentRepository extends JpaRepository <Environment, Long> {
}

