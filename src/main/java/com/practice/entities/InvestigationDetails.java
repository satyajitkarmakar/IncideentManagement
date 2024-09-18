package com.practice.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Table(name = "Investigation_Details")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class InvestigationDetails {

    @Id
    @Column(name = "Investigation_Details_Id")
    private int investigationDetailsId;

    @Column(name = "Findings")
    private String findings;

    @Column(name = "Suggestions")
    private String suggestions;

    @Column(name = "Investigation_Date")
    private LocalDate investigationDate;

    @ManyToOne
    @JoinColumn(
            name = "Incident_Id"
    )
    private Incidents incidents;
}
