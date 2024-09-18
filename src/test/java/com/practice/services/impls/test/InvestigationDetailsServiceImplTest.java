package com.practice.services.impls.test;

import com.practice.dtos.InvestigationReportDTO;
import com.practice.entities.Incidents;
import com.practice.entities.InvestigationDetails;
import com.practice.exceptions.IdNotFoundException;
import com.practice.exceptions.InvalidDateException;
import com.practice.repositories.IncidentsRepository;
import com.practice.repositories.InvestigationDetailsRepository;
import com.practice.services.impls.InvestigationDetailsServiceImpl;
import com.practice.utilities.InvestigationReportMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;


class InvestigationDetailsServiceImplTest {
    @InjectMocks
    private InvestigationDetailsServiceImpl investigationDetailsService;

    @Mock
    private InvestigationDetailsRepository investigationDetailsRepository;

    @Mock
    private IncidentsRepository incidentsRepository;

    @Mock
    private InvestigationReportMapper investigationReportMapper;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }


    @Test
    void should_create_a_new_investigation_report(){
        InvestigationReportDTO investigationReport = new InvestigationReportDTO();
        investigationReport.setFindings("resolved");
        investigationReport.setIncidentId("2024-5431");
        investigationReport.setInvestigationDate(LocalDate.now());

        Incidents incident = new Incidents();
        incident.setIncidentId("2024-5431");

        when(incidentsRepository.findById(investigationReport.getIncidentId())).thenReturn(Optional.of(incident));

        InvestigationDetails createInvestigationDetails = new InvestigationDetails();
        createInvestigationDetails.setFindings("resolved");
        createInvestigationDetails.setIncidents(incident);
        createInvestigationDetails.setInvestigationDate(LocalDate.now());

        InvestigationDetails investigationDetails = new InvestigationDetails();
        investigationDetails.setFindings("resolved");
        investigationDetails.setInvestigationDate(LocalDate.now());
        investigationDetails.setIncidents(incident);

        when(investigationReportMapper.toInvestigationDetails(any(InvestigationReportDTO.class))).thenReturn(investigationDetails);
        when(investigationDetailsRepository.save(investigationDetails)).thenReturn(createInvestigationDetails);

        InvestigationReportDTO createInvestigation = new InvestigationReportDTO();
        createInvestigation.setFindings("resolved");
        createInvestigation.setIncidentId("2024-5431");
        createInvestigation.setInvestigationDate(LocalDate.now());
        when(investigationReportMapper.toInvestigationReport(createInvestigationDetails)).thenReturn(createInvestigation);

        InvestigationReportDTO newInvestigation = investigationDetailsService.createInvestigationReport(investigationReport);

        assertNotNull(newInvestigation);
        assertEquals(investigationReport.getIncidentId(), newInvestigation.getIncidentId());
        assertEquals(investigationReport.getFindings(), newInvestigation.getFindings());

        verify(incidentsRepository, times(1)).findById("2024-5431");
    }

    @Test
    void should_throw_NullPointerException_when_InvestigationReportDTO_NULL(){
        assertThrows(NullPointerException.class, () -> {
            investigationDetailsService.createInvestigationReport(null);
        });
    }

    @Test
    void should_throw_IdNotFoundException_when_IncidentId_Invalid(){
        String id = "123";
        when(incidentsRepository.findById(id)).thenReturn(Optional.empty());
        InvestigationReportDTO investigationReportDTO = new InvestigationReportDTO();
        assertThrows(IdNotFoundException.class, () -> {
            investigationDetailsService.createInvestigationReport(investigationReportDTO);
        });
    }

    @Test
    void should_Find_All_Investigations(){
        List<InvestigationDetails> investigationDetailsList = new ArrayList<>();
        InvestigationDetails investigationDetails = new InvestigationDetails();
        investigationDetails.setSuggestions("suggestions");
        investigationDetails.setFindings("findings");

        investigationDetailsList.add(investigationDetails);

        InvestigationReportDTO investigationReport = new InvestigationReportDTO();
        investigationReport.setSuggestions("suggestions");
        investigationReport.setFindings("findings");

        when(investigationDetailsRepository.findAll()).thenReturn(investigationDetailsList);
        when(investigationReportMapper.toInvestigationReport(any(InvestigationDetails.class))).thenReturn(investigationReport);

        List<InvestigationReportDTO> investigationReportList = investigationDetailsService.getAllInvestigationReport();

        assertNotNull(investigationReportList);
        assertEquals(investigationReportList.get(0).getFindings(), investigationReportList.get(0).getFindings());

    }

    @Test
    void should_Not_Find_All_Investigations(){
        List<InvestigationDetails> investigationDetailsList = new ArrayList<>();
        when(investigationDetailsRepository.findAll()).thenReturn(investigationDetailsList);
        List<InvestigationReportDTO> investigationReportList = investigationDetailsService.getAllInvestigationReport();
        assertNotNull(investigationReportList);

    }

    void should_throw_InvalidDateException_When_InvestigationDate_Invalid_Future(){
        LocalDate investigationDate = LocalDate.now().plusDays(1);
        assertThrows(InvalidDateException.class,
                () -> {
                    investigationDetailsService.validateInvestigationDate(investigationDate);
                });
    }

    @Test
    void should_throw_InvalidDateException_When_InvalidDate_Invalid_Past(){
        LocalDate investigationDate = LocalDate.now().minusDays(1);
        assertThrows(InvalidDateException.class,
                () -> {
                    investigationDetailsService.validateInvestigationDate(investigationDate);
                });
    }
}