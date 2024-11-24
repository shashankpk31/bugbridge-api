package com.shashankpk.bug_bridge.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class DefectDTO {
    private Long id;
    private String title;
    private String description;
    private Long reportedById;
    private Long environmentId;
    private String status;

}
