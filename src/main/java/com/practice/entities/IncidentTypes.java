package com.practice.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "Incident_Types")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class IncidentTypes {
    @Id
    @Column(name = "Incident_Type_Id")
    private int incidentTypeId;

    @Column(name = "Type")
    private int type;

    @Column(name = "Expected_SLA_In_Days")
    private int expectedSLAInDays;
}
