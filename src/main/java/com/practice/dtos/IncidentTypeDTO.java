package com.practice.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class IncidentTypeDTO {
    private int incidentTypeId;

    private int type;

    private int expectedSLAInDays;
}
