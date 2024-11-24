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
public class Defect {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    private String description;
    private String status;
    private Date reportedOn;
    private Date lastModifiedOn;
    @ManyToOne
    @JoinColumn(name = "reported_by")
    private User reportedBy;
    @ManyToOne
    @JoinColumn(name = "current_owner")
    private User currentOwner;
    @ManyToOne
    @JoinColumn(name = "environment_id")
    private Environment environment;
}