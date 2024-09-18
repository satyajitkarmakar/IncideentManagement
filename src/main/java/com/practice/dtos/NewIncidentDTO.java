package com.practice.dtos;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class NewIncidentDTO {
    private String incidentId;

    @PastOrPresent(message = "Incident Date Should Be In Past Or Present")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate incidentDate;

    @PastOrPresent
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate reportDate;

    private int incidentReportedByUserId;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate resolutionETA;

//    @NotNull(message = "Incident summary cannot be null")
    @Size(min = 10, max = 255, message = "Incident summary must be between 10 to 255 characters")
    private String incidentSummary;

    @NotNull(message = "Incident details cannot be null")
    @Size(min = 10, max = 255, message = "Incident details must be between 10 to 255 characters")
    private String incidentDetails;

    @NotNull
    private int bookingId;

    private IncidentStatusValue status;

    @NotNull(message = "Incident type id is required")
    private int incidentTypeId;

}
