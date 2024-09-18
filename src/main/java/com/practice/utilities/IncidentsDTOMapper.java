package com.practice.utilities;

import com.practice.dtos.IncidentsDTO;
import com.practice.entities.IncidentTypes;
import com.practice.entities.Incidents;
import org.springframework.stereotype.Service;

@Service
public class IncidentsDTOMapper {

    public Incidents toIncidents(IncidentsDTO incidentsDTO){
        Incidents incidents = new Incidents();

        incidents.setIncidentId(incidentsDTO.getIncidentId());
        incidents.setIncidentDate(incidentsDTO.getIncidentDate());
        incidents.setReportDate(incidentsDTO.getReportDate());
        incidents.setIncidentReportedByUserId(incidentsDTO.getIncidentReportedByUserId());
        incidents.setResolutionETA(incidentsDTO.getResolutionETA());
        incidents.setInvestigatedByUserId(incidentsDTO.getInvestigatedByUserId());
        incidents.setIncidentSummary(incidentsDTO.getIncidentSummary());
        incidents.setIncidentDetails(incidentsDTO.getIncidentDetails());
        incidents.setBookingId(incidentsDTO.getBookingId());
        incidents.setStatus(incidentsDTO.getStatus());

        IncidentTypes incidentTypes = new IncidentTypes();
        incidentTypes.setIncidentTypeId(incidentsDTO.getIncidentTypeId());

        incidents.setIncidentTypes(incidentTypes);

        return incidents;
    }

    public IncidentsDTO toIncidentDTO(Incidents incidents){
        IncidentsDTO incidentsDTO = new IncidentsDTO();

        incidentsDTO.setIncidentId(incidents.getIncidentId());
        incidentsDTO.setIncidentDate(incidents.getIncidentDate());
        incidentsDTO.setReportDate(incidents.getReportDate());
        incidentsDTO.setIncidentReportedByUserId(incidents.getIncidentReportedByUserId());
        incidentsDTO.setResolutionETA(incidents.getResolutionETA());
        incidentsDTO.setInvestigatedByUserId(incidents.getInvestigatedByUserId());
        incidentsDTO.setIncidentSummary(incidents.getIncidentSummary());
        incidentsDTO.setIncidentDetails(incidents.getIncidentDetails());
        incidentsDTO.setBookingId(incidents.getBookingId());
        incidentsDTO.setStatus(incidents.getStatus());
        incidentsDTO.setIncidentTypeId(incidents.getIncidentTypes().getIncidentTypeId());

        return incidentsDTO;
    }

}
