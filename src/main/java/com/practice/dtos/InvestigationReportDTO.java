package com.practice.dtos;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class InvestigationReportDTO {
    private int investigationDetailsId;

    @NotBlank(message = "Finding should not be null")
    private String findings;

    @NotBlank(message = "Suggestion should not be null")
    private String suggestions;

    @JsonFormat(pattern = "yyyy-MM-dd")
    @NotNull(message = "Investigation Date should not be null")
    private LocalDate investigationDate;

    private String incidentId;

}
