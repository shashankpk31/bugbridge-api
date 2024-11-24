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
public class DefectComment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "defect_id")
    private Defect defect;
    private String comment;
    private Date commentedOn;
    @ManyToOne
    @JoinColumn(name = "commented_by")
    private User commentedBy;
}