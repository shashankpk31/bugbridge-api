package com.shashankpk.bug_bridge.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class DefectHistory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "defect_id")
    private Defect defect;
    private String oldStatus;
    private String newStatus;
    private String comment;
    private Date changedOn;
    @ManyToOne
    @JoinColumn(name = "changed_by")
    private User changedBy;
}
