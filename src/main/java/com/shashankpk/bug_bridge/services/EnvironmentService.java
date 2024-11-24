package com.shashankpk.bug_bridge.services;


import com.shashankpk.bug_bridge.dto.EnvironmentDTO;
import com.shashankpk.bug_bridge.models.Environment;
import com.shashankpk.bug_bridge.models.User;
import com.shashankpk.bug_bridge.repositories.EnvironmentRepository;
import com.shashankpk.bug_bridge.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class EnvironmentService {
    @Autowired
    private EnvironmentRepository environmentRepository;
    @Autowired
    private UserRepository userRepository;

    public void createEnvironment(EnvironmentDTO environmentDTO, String creatorUsername) {
        Environment environment = new Environment();
        environment.setName(environmentDTO.getName());
        environment.setDescription(environmentDTO.getDescription());
        User creator = userRepository.findByUsername(creatorUsername).orElse(null);
        environment.setCreatedBy(creator);
        environmentRepository.save(environment);
    }

    public List<EnvironmentDTO> getAllEnvironments() {
        List<Environment> environments = environmentRepository.findAll();
        return environments.stream().map(env -> new EnvironmentDTO(env.getId(), env.getName(), env.getDescription(), env.getCreatedBy().getUsername())).collect(Collectors.toList());
    }

    public void updateEnvironment(Long id, EnvironmentDTO environmentDTO) {
        Environment environment = environmentRepository.findById(id).orElseThrow(() -> new RuntimeException("Environment not found"));
        environment.setName(environmentDTO.getName());
        environment.setDescription(environmentDTO.getDescription());
        environmentRepository.save(environment);
    }

    public void deleteEnvironment(Long id) {
        environmentRepository.deleteById(id);
    }
}