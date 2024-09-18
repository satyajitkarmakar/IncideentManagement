package com.practice.controllers;

import com.practice.dtos.IncidentsDTO;
import com.practice.dtos.InvestigationReportDTO;
import com.practice.dtos.NewIncidentDTO;
import com.practice.exceptions.CannotReportIncidentException;
import com.practice.exceptions.IdNotFoundException;
import com.practice.services.IncidentsService;
import com.practice.services.InvestigationDetailsService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author Satyajit Karmakar
 * This class represents Rest API endpoints for Incident resource
 */
@RestController
@RequestMapping("/api")
@CrossOrigin("http://localhost:4200/")
@Tag(name="Incident Endpoint")
public class IncidentsController {

    private final IncidentsService incidentsService;
    private final InvestigationDetailsService investigationDetailsService;
    /**
     * Constructor for the IncidentController class.
     * @param incidentsService service for handling incident-related operations.
     */
    @Autowired
    public IncidentsController(IncidentsService incidentsService,
                               InvestigationDetailsService investigationDetailsService) {
        this.incidentsService = incidentsService;
        this.investigationDetailsService = investigationDetailsService;
    }

    /**
     * POST method for creating a new incident.
     * @param newIncidentDTO data transfer object containing data for the new incident.
     * @return ResponseEntity with a success message containing the Incident ID if creation is successful,
     * or an error message if an exception occurs during the creation process.
     */
    @PostMapping("/incidents/report")

    @Operation(summary = "This end point is used to report a new incident")
    public ResponseEntity<NewIncidentDTO> createNewIncident(

            @Valid @RequestBody NewIncidentDTO newIncidentDTO){
        try{
            NewIncidentDTO incidentDto = incidentsService.createNewIncident(newIncidentDTO);
            return new ResponseEntity<>(incidentDto, HttpStatus.CREATED);
        }catch (IdNotFoundException | CannotReportIncidentException e){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * GET method for retrieving an incident by ID.
     * @param incidentId The ID of the incident to retrieve.
     * @return ResponseEntity with the incident information if found,
     * or an error response if the incident is not found.
     */

    @GetMapping("/incidents/{incidentId}")
    @Operation(summary = "Using this end point one can find an incident by an id")
    public ResponseEntity<IncidentsDTO> getAnIncidentById(
            
            @PathVariable("incidentId") String incidentId
    ){
        try{
            IncidentsDTO incidentsDTO = incidentsService.findAnIncidentById(incidentId);
            return new ResponseEntity<>(incidentsDTO, HttpStatus.OK);
        }catch (IdNotFoundException e){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    /**
     * GET method for retrieving all the pending incidents.
     * @return ResponseEntity with a list of all pending incident if successful,
     * or an error response if an exception occurs during the retrieval process.
     */
    @GetMapping("/incidents")
    @Operation(summary = "Using this end point we can get all the pending incidents")
    public ResponseEntity<List<IncidentsDTO>> getAllPendingIncidents(){
        List<IncidentsDTO> incidentsList = incidentsService.getAllPendingIncidents();
        if(incidentsList.isEmpty()){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(incidentsList, HttpStatus.OK);
    }

    /**
     * PUT method for updating an incident with investigation report.
     * @param incidentId The ID of the incident to update.
     * @param investigationReportDTO DTO containing the investigation report.
     * @return ResponseEntity indicating the status of update operation.
     */
    @PutMapping("/incidents/{incidentId}/investigationreport")
    @Operation(summary = "Using this end point a pending incident will be updated")
    public ResponseEntity<InvestigationReportDTO> updateAnIncident(
           @PathVariable("incidentId") String incidentId, @Valid @RequestBody InvestigationReportDTO investigationReportDTO
    ){
        InvestigationReportDTO investigationReport = incidentsService.updateAnIncident(incidentId, investigationReportDTO);
        if(investigationReport == null){
            return new ResponseEntity<>(HttpStatus.NOT_MODIFIED);
        }
        return new ResponseEntity<>(investigationReport,HttpStatus.CREATED);
    }

    @GetMapping("/incidents/investigation")
    @Operation(summary = "this end point is used to find all the investigations")
    public ResponseEntity<List<InvestigationReportDTO>> getAllInvestigation(){
        List<InvestigationReportDTO> investigationReportDTOS = investigationDetailsService.getAllInvestigationReport();
        if(!investigationReportDTOS.isEmpty()){
            return new ResponseEntity<>(investigationReportDTOS, HttpStatus.OK);
        }
        else{
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

}
