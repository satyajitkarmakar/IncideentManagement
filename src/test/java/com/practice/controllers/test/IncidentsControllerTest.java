package com.practice.controllers.test;

import com.practice.controllers.IncidentsController;
import com.practice.dtos.*;
import com.practice.exceptions.CannotReportIncidentException;
import com.practice.exceptions.IdNotFoundException;
import com.practice.services.IncidentsService;
import com.practice.services.InvestigationDetailsService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class IncidentsControllerTest {
    private MockMvc mockMvc;
    @InjectMocks
    private IncidentsController incidentsController;
    @Mock
    private IncidentsService incidentsService;
    @Mock
    private InvestigationDetailsService investigationDetailsService;
    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc= MockMvcBuilders.standaloneSetup(incidentsController).build();
    }
    @Test
    void shouldFindAllPendingIncidents(){


            List<IncidentsDTO> incidentsList = new ArrayList<>();

            IncidentsDTO incidentsDTO = new IncidentsDTO();
            incidentsDTO.setIncidentId("2024-2354");
            incidentsDTO.setStatus(IncidentStatusValue.valueOf("PENDING"));

            incidentsList.add(incidentsDTO);

            when(incidentsService.getAllPendingIncidents()).thenReturn(incidentsList);

            ResponseEntity<?> responseEntity = incidentsController.getAllPendingIncidents();
            List<IncidentsDTO> actualList = (List<IncidentsDTO>) responseEntity.getBody();
            assertNotNull(actualList);
            assertTrue(!actualList.isEmpty());


    }

    @Test
    void shouldFindAllPendingIncidents_Negetive(){

            List<IncidentsDTO> incidentsList = new ArrayList<>();

            IncidentsDTO incidentsDTO = new IncidentsDTO();
            when(incidentsService.getAllPendingIncidents()).thenReturn(incidentsList);

            ResponseEntity<?> responseEntity = incidentsController.getAllPendingIncidents();
            assertEquals(404, responseEntity.getStatusCodeValue());
            assertNull(responseEntity.getBody());

    }

    @Test
    void testUri_Should_GetAllPendingIncidents(){
        List<IncidentsDTO> incidentsList = new ArrayList<>();

        IncidentsDTO incidentsDTO = new IncidentsDTO();
        incidentsDTO.setIncidentId("2024-2354");
        incidentsDTO.setStatus(IncidentStatusValue.valueOf("PENDING"));

        incidentsList.add(incidentsDTO);

        when(incidentsService.getAllPendingIncidents()).thenReturn(incidentsList);

        ResponseEntity<?> responseEntity = incidentsController.getAllPendingIncidents();
        try{
            MvcResult result = mockMvc.perform(get("http://localhost:8089/api/incidents"))
                    .andExpect(status().isOk())
                    .andReturn();
        }catch(Exception e){
            assertTrue(false);
        }
    }

    @Test
    void testUri_Should_Not_GetAllPendingIncidents(){
        List<IncidentsDTO> incidentsList = new ArrayList<>();

        IncidentsDTO incidentsDTO = new IncidentsDTO();
        incidentsDTO.setIncidentId("2024-2354");
        incidentsDTO.setStatus(IncidentStatusValue.valueOf("PENDING"));

        incidentsList.add(incidentsDTO);

        when(incidentsService.getAllPendingIncidents()).thenReturn(incidentsList);

        ResponseEntity<?> responseEntity = incidentsController.getAllPendingIncidents();
        try{
            MvcResult result = mockMvc.perform(get("http://localhost:8090/api/incident"))
                    .andExpect(status().isNotFound())
                    .andReturn();
        }catch(Exception e){
            assertFalse(true);
        }
    }

    @Test
    void should_Find_An_Incident_By_Id(){

        String incidentId = "2024-2354";
        IncidentsDTO incidentsDTO = new IncidentsDTO();
        incidentsDTO.setIncidentId("2024-2354");
        incidentsDTO.setStatus(IncidentStatusValue.valueOf("PENDING"));

        when(incidentsService.findAnIncidentById(incidentId)).thenReturn(incidentsDTO);

        ResponseEntity<?> responseEntity = incidentsController.getAnIncidentById(incidentId);
        IncidentsDTO newIncidentDto = (IncidentsDTO) responseEntity.getBody();

        assertEquals(incidentsDTO.getIncidentId(), newIncidentDto.getIncidentId());


    }

    @Test
    void should_Throw_IdNotFoundException(){

        String incidentId = "2024-235";
        IncidentsDTO incidentsDTO = new IncidentsDTO();

        when(incidentsService.findAnIncidentById(incidentId)).thenThrow(IdNotFoundException.class);

        ResponseEntity<?> responseEntity = incidentsController.getAnIncidentById(incidentId);

        assertEquals(404, responseEntity.getStatusCodeValue());

    }

    @Test
    void test_uri_should_Find_An_Incident_By_Id(){

        String incidentId = "2024-2354";
        IncidentsDTO incidentsDTO = new IncidentsDTO();
        incidentsDTO.setIncidentId("2024-2354");
        incidentsDTO.setStatus(IncidentStatusValue.valueOf("PENDING"));

        when(incidentsService.findAnIncidentById(incidentId)).thenReturn(incidentsDTO);

        ResponseEntity<?> responseEntity = incidentsController.getAnIncidentById(incidentId);
        IncidentsDTO newIncidentDto = (IncidentsDTO) responseEntity.getBody();

//        assertEquals(incidentsDTO.getIncidentId(), newIncidentDto.getIncidentId());

        try{
            MvcResult result = mockMvc.perform(get("http://localhost:8089/api/incidents/2024-2354"))
                    .andExpect(status().isOk())
                    .andReturn();
        }catch(Exception e){
            assertTrue(false);
        }
    }

    @Test
    void test_uri_should_not_Find_An_Incident_By_Id(){

        String incidentId = "2024-2354";
        IncidentsDTO incidentsDTO = new IncidentsDTO();
        incidentsDTO.setIncidentId("2024-2354");
        incidentsDTO.setStatus(IncidentStatusValue.valueOf("PENDING"));

        when(incidentsService.findAnIncidentById(incidentId)).thenReturn(incidentsDTO);

        ResponseEntity<?> responseEntity = incidentsController.getAnIncidentById(incidentId);
        IncidentsDTO newIncidentDto = (IncidentsDTO) responseEntity.getBody();

        try{
            MvcResult result = mockMvc.perform(get("http://localhost:8090/api/incident/2024-2354"))
                    .andExpect(status().isNotFound())
                    .andReturn();
        }catch(Exception e){
            assertFalse(true);
        }
    }

    @Test
    void shouldFindAllInvestigationReport(){
        List<InvestigationReportDTO> investigationReports = new ArrayList<>();
        InvestigationReportDTO investigationReport = new InvestigationReportDTO();
        investigationReport.setSuggestions("suggestions");
        investigationReport.setFindings("findings");
        investigationReports.add(investigationReport);

        when(investigationDetailsService.getAllInvestigationReport()).thenReturn(investigationReports);

        ResponseEntity<?> responseEntity = incidentsController.getAllInvestigation();
        List<InvestigationReportDTO> actualList = (List<InvestigationReportDTO>) responseEntity.getBody();
        assertTrue(actualList.size() > 0);
    }

    @Test
    void shouldNotFindAllInvestigationReport(){

        List<InvestigationReportDTO> investigationReports = new ArrayList<>();

        when(investigationDetailsService.getAllInvestigationReport()).thenReturn(investigationReports);

        ResponseEntity<?> responseEntity = incidentsController.getAllInvestigation();

        assertNull(responseEntity.getBody());

    }

    @Test
    void testUri_Should_AllInvestigationReport(){
        List<InvestigationReportDTO> investigationReports = new ArrayList<>();
        InvestigationReportDTO investigationReport = new InvestigationReportDTO();
        investigationReport.setSuggestions("suggestions");
        investigationReport.setFindings("findings");
        investigationReports.add(investigationReport);

        when(investigationDetailsService.getAllInvestigationReport()).thenReturn(investigationReports);

        ResponseEntity<?> responseEntity = incidentsController.getAllInvestigation();
        List<InvestigationReportDTO> actualList = (List<InvestigationReportDTO>) responseEntity.getBody();
        try{
            MvcResult result = mockMvc.perform(get("http://localhost:8089/api/incidents/investigation"))
                    .andExpect(status().isOk())
                    .andReturn();
        }catch(Exception e){
            assertTrue(false);
        }
    }

    @Test
    void should_create_new_incident(){
        NewIncidentDTO incidentDTO = new NewIncidentDTO();
        incidentDTO.setIncidentDate(LocalDate.now());
        incidentDTO.setReportDate(LocalDate.now());
        incidentDTO.setIncidentDetails("write in details");

        NewIncidentDTO createdIncidentDTO = new NewIncidentDTO();
        createdIncidentDTO.setIncidentDate(LocalDate.now());
        createdIncidentDTO.setReportDate(LocalDate.now());
        createdIncidentDTO.setIncidentDetails("write in details");

        when(incidentsService.createNewIncident(incidentDTO)).thenReturn(createdIncidentDTO);

        ResponseEntity<?> responseEntity = incidentsController.createNewIncident(createdIncidentDTO);
        NewIncidentDTO getIncidentDto = (NewIncidentDTO) responseEntity.getBody();
        assertNotNull(getIncidentDto);
        assertEquals(createdIncidentDTO.getIncidentDate(), getIncidentDto.getIncidentDate());
        assertEquals(createdIncidentDTO.getIncidentDetails(), getIncidentDto.getIncidentDetails());
    }
    @Test
    void should_not_create_new_incident(){
        NewIncidentDTO incidentDTO = new NewIncidentDTO();
        incidentDTO.setIncidentDate(LocalDate.now());
        incidentDTO.setReportDate(LocalDate.now().plusDays(5));
        incidentDTO.setIncidentDetails("write in details");

        when(incidentsService.createNewIncident(incidentDTO)).thenThrow(CannotReportIncidentException.class);

        ResponseEntity<?> responseEntity = incidentsController.createNewIncident(incidentDTO);
        assertEquals(500, responseEntity.getStatusCodeValue());
    }

    @Test
    void should_update_an_incident(){
        String incidentId = "2024-1234";
        InvestigationReportDTO investigationReport = new InvestigationReportDTO();
        investigationReport.setFindings("incident is resolved");
        investigationReport.setIncidentId(incidentId);
        investigationReport.setSuggestions("write some suggestions");
        InvestigationReportDTO createdReport = new InvestigationReportDTO();
        createdReport.setFindings("incident is resolved");
        createdReport.setIncidentId(incidentId);
        createdReport.setSuggestions("write some suggestions");
        when(incidentsService.updateAnIncident(incidentId, investigationReport)).thenReturn(createdReport);

        ResponseEntity<?> responseEntity = incidentsController.updateAnIncident(incidentId, createdReport);
        InvestigationReportDTO investigationReportDTO = (InvestigationReportDTO)responseEntity.getBody();
        assertNotNull(investigationReportDTO);
        assertEquals(investigationReportDTO.getFindings(), createdReport.getFindings());
        assertEquals(investigationReportDTO.getIncidentId(), createdReport.getIncidentId());
    }

//    @Test
//    void should_not_update_an_incident(){
//        String incidentId = "2024-1234";
//        InvestigationReportDTO investigationReport = new InvestigationReportDTO();
//        investigationReport.setFindings("incident is unresolved");
//        investigationReport.setIncidentId(incidentId);
//        investigationReport.setSuggestions("write some suggestions");
//        InvestigationReportDTO createdReport = new InvestigationReportDTO();
//        createdReport.setFindings("incident is unresolved");
//        createdReport.setIncidentId(incidentId);
//        createdReport.setSuggestions("write some suggestions");
//        when(incidentsService.updateAnIncident(incidentId, investigationReport)).thenReturn(createdReport);
//
//        ResponseEntity<?> responseEntity = incidentsController.updateAnIncident(incidentId, createdReport);
//        InvestigationReportDTO investigationReportDTO = (InvestigationReportDTO)responseEntity.getBody();
//        assertNull(investigationReportDTO);
//        assertEquals(305, responseEntity.getStatusCodeValue());
//    }
}