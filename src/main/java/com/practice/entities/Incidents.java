package com.practice.entities;

import com.practice.dtos.IncidentStatusValue;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Table(name = "Incidents")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Incidents {

    @Id
    @Column(name = "Incident_Id")
    private String incidentId;

    @Column(name = "Incident_Date")
    private LocalDate incidentDate;

    @Column(name = "Report_Date")
    private LocalDate reportDate;

    @Column(name = "Incident_Reported_By_User_Id")
    private int incidentReportedByUserId;

    @Column(name = "Resolution_ETA")
    private LocalDate resolutionETA;

    @Column(name = "Investigated_By_User_Id")
    private int investigatedByUserId;

    @Column(name = "Incident_Summary")
    private String incidentSummary;

    @Column(name = "Incident_Details")
    private String incidentDetails;

    @Column(name = "Booking_Id")
    private int bookingId;

    @Column(name = "Status")
    @Enumerated(EnumType.STRING)
    private IncidentStatusValue status;

    @ManyToOne
    @JoinColumn(
          name = "Incident_Type_Id"
    )
    private IncidentTypes incidentTypes;

}
