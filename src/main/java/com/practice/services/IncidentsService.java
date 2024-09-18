package com.practice.services;

import com.practice.dtos.IncidentsDTO;
import com.practice.dtos.InvestigationReportDTO;
import com.practice.dtos.NewIncidentDTO;

import java.util.List;

public interface IncidentsService {
    //this method will create a new incident
    NewIncidentDTO createNewIncident(NewIncidentDTO newIncidentDTO);

    //this method will find an incident based on an id
    IncidentsDTO findAnIncidentById(String incidentId);

    // this method will find all the pending incidents present in the database
    List<IncidentsDTO> getAllPendingIncidents();

    //this method will update and given incident based on an id
    InvestigationReportDTO updateAnIncident(String incidentId, InvestigationReportDTO investigationReportDTO);

}
