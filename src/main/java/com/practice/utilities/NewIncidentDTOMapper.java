package com.practice.utilities;

import com.practice.dtos.NewIncidentDTO;
import com.practice.entities.IncidentTypes;
import com.practice.entities.Incidents;
import org.springframework.stereotype.Service;

@Service
public class NewIncidentDTOMapper {

    public Incidents toIncidents(NewIncidentDTO newIncidentDTO){
        if(newIncidentDTO == null){
            throw new NullPointerException("IncidentDTO can not be null.");
        }
        Incidents incidents = new Incidents();

        incidents.setIncidentId(newIncidentDTO.getIncidentId());
        incidents.setIncidentDate(newIncidentDTO.getIncidentDate());
        incidents.setReportDate(newIncidentDTO.getReportDate());
        incidents.setIncidentReportedByUserId(newIncidentDTO.getIncidentReportedByUserId());
        incidents.setResolutionETA(newIncidentDTO.getResolutionETA());
        incidents.setIncidentSummary(newIncidentDTO.getIncidentSummary());
        incidents.setIncidentDetails(newIncidentDTO.getIncidentDetails());
        incidents.setBookingId(newIncidentDTO.getBookingId());
        incidents.setStatus(newIncidentDTO.getStatus());

        IncidentTypes incidentTypes = new IncidentTypes();
        incidentTypes.setIncidentTypeId(newIncidentDTO.getIncidentTypeId());

        incidents.setIncidentTypes(incidentTypes);

        return incidents;
    }

    public NewIncidentDTO toNewIncidentDTO(Incidents incidents){
        if(incidents == null){
            throw new NullPointerException("Incident can not be null.");
        }

        NewIncidentDTO newIncidentDTO = new NewIncidentDTO();

        newIncidentDTO.setIncidentId(incidents.getIncidentId());
        newIncidentDTO.setIncidentDate(incidents.getIncidentDate());
        newIncidentDTO.setReportDate(incidents.getReportDate());
        newIncidentDTO.setIncidentReportedByUserId(incidents.getIncidentReportedByUserId());
        newIncidentDTO.setResolutionETA(incidents.getResolutionETA());
        newIncidentDTO.setIncidentSummary(incidents.getIncidentSummary());
        newIncidentDTO.setIncidentDetails(incidents.getIncidentDetails());
        newIncidentDTO.setBookingId(incidents.getBookingId());
        newIncidentDTO.setStatus(incidents.getStatus());
        newIncidentDTO.setIncidentTypeId(incidents.getIncidentTypes().getIncidentTypeId());

        return newIncidentDTO;
    }


}
