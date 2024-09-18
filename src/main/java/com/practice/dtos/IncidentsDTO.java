package com.practice.dtos;


import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class IncidentsDTO {
    private String incidentId;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate incidentDate;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate reportDate;

    private int incidentReportedByUserId;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate resolutionETA;

    private int investigatedByUserId;

    private String incidentSummary;

    private String incidentDetails;

    private int bookingId;

    private IncidentStatusValue status;

    private int incidentTypeId;

    private int investigationDetailsId;
}
