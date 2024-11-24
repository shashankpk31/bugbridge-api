package com.shashankpk.bug_bridge.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EnvironmentDTO {
    private Long id;
    private String name;
    private String description;
    private String username;
}

