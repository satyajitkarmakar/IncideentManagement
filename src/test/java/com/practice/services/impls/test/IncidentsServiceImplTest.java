package com.practice.services.impls.test;

import com.practice.dtos.IncidentStatusValue;
import com.practice.dtos.IncidentsDTO;
import com.practice.dtos.InvestigationReportDTO;
import com.practice.dtos.NewIncidentDTO;
import com.practice.entities.IncidentTypes;
import com.practice.entities.Incidents;
import com.practice.exceptions.CannotReportIncidentException;
import com.practice.exceptions.IdFoundException;
import com.practice.exceptions.IdNotFoundException;
import com.practice.repositories.IncidentTypeRepository;
import com.practice.repositories.IncidentsRepository;
import com.practice.services.impls.IncidentsServiceImpl;
import com.practice.services.impls.InvestigationDetailsServiceImpl;
import com.practice.utilities.IncidentsDTOMapper;
import com.practice.utilities.NewIncidentDTOMapper;
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

class IncidentsServiceImplTest {
    @InjectMocks
    private IncidentsServiceImpl incidentsService;
    @Mock
    private NewIncidentDTO newIncidentDTO;
    @Mock
    private IncidentsRepository incidentsRepository;
    @Mock
    private IncidentTypeRepository incidentTypeRepository;
    @Mock
    private IncidentsDTOMapper incidentsDTOMapper;
    @Mock
    private NewIncidentDTOMapper newIncidentDTOMapper;
    @Mock
    private InvestigationDetailsServiceImpl investigationDetailsServiceImpl;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void should_create_a_new_incident(){
        IncidentTypes incidentTypes = new IncidentTypes();
        incidentTypes.setIncidentTypeId(20006);

        NewIncidentDTO incidentsDTO = new NewIncidentDTO();
        incidentsDTO.setIncidentId("2024-2543");
        incidentsDTO.setIncidentDate(LocalDate.now());
        incidentsDTO.setReportDate(LocalDate.now());
        incidentsDTO.setIncidentReportedByUserId(20);
        incidentsDTO.setResolutionETA(LocalDate.now());
        incidentsDTO.setIncidentReportedByUserId(2);
        incidentsDTO.setIncidentDetails("Details");
        incidentsDTO.setIncidentSummary("summary");
        incidentsDTO.setStatus(IncidentStatusValue.valueOf("PENDING"));
        incidentsDTO.setIncidentTypeId(incidentTypes.getIncidentTypeId());


        when(incidentTypeRepository.findById(incidentsDTO.getIncidentTypeId())).thenReturn(Optional.of(incidentTypes));

        Incidents mappedIncident = new Incidents();
        mappedIncident.setIncidentId("2024-2543");
        mappedIncident.setIncidentDate(LocalDate.now());
        mappedIncident.setReportDate(LocalDate.now());
        mappedIncident.setIncidentReportedByUserId(20);
        mappedIncident.setResolutionETA(LocalDate.now());
        mappedIncident.setIncidentReportedByUserId(2);
        mappedIncident.setIncidentDetails("Details");
        when(newIncidentDTOMapper.toIncidents(incidentsDTO)).thenReturn(mappedIncident);
        when(incidentsRepository.save(any(Incidents.class))).thenReturn(mappedIncident);

        NewIncidentDTO incidentDto = incidentsService.createNewIncident(incidentsDTO);

//        assertEquals(incidentsDTO.getIncidentId(), incidentDto.getIncidentId());
        assertEquals(incidentsDTO.getIncidentTypeId(), incidentTypes.getIncidentTypeId());

        verify(incidentTypeRepository, times(1)).findById(incidentsDTO.getIncidentTypeId());
        verify(newIncidentDTOMapper, times(1)).toIncidents(incidentsDTO);
        verify(incidentsRepository, times(1)).save(any(Incidents.class));
    }

    @Test
    void should_find_an_incident_by_a_given_id(){
        IncidentTypes incidentTypes = new IncidentTypes(
                20006,
                6,
                12
        );
        Incidents incidents = new Incidents(
                "2024-2375",
                LocalDate.now(),
                LocalDate.now(),
                20,
                LocalDate.now(),
                40,
                "summary",
                "details",
                2232,
                IncidentStatusValue.valueOf("PENDING"),
                incidentTypes
        );

        when(incidentsRepository.findById("2024-2375"))
                .thenReturn(Optional.of(incidents));

        when(incidentsDTOMapper.toIncidentDTO(any(Incidents.class)))
                .thenReturn(
                        new IncidentsDTO(
                                "2024-2375",
                                LocalDate.now(),
                                LocalDate.now(),
                                20,
                                LocalDate.now(),
                                40,
                                "summary",
                                "details",
                                2232,
                                IncidentStatusValue.valueOf("PENDING"),
                                20006,
                                0
                        )
                );
        IncidentsDTO incidentsDTO = incidentsService.findAnIncidentById("2024-2375");

        verify(incidentsRepository, times(1)).findById("2024-2375");

        assertNotNull(incidentsDTO);
        assertEquals(incidentsDTO.getIncidentId(), incidents.getIncidentId());
        assertEquals(incidentsDTO.getIncidentTypeId(), incidents.getIncidentTypes().getIncidentTypeId());
    }

    @Test
    void should_throw_IdNotFoundException_when_ID_invalid(){
        String id = "123";
        when(incidentsRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(IdNotFoundException.class, ()->
            incidentsService.findAnIncidentById(id)
        );
    }

    @Test
    void should_throw_InvalidDateException_When_ReportDate_Invalid_Future(){
        LocalDate reportDate = LocalDate.now().plusDays(1);
        assertThrows(CannotReportIncidentException.class,
                () -> {
                    incidentsService.validateReportDate(reportDate);
                });
    }

    @Test
    void should_throw_InvalidDateException_When_ReportDate_Invalid_Past(){
        LocalDate reportDate = LocalDate.now().minusDays(1);
        assertThrows(CannotReportIncidentException.class,
                () -> {
                    incidentsService.validateReportDate(reportDate);
                });
    }

    @Test
    void should_throw_InvalidDateException_When_IncidentDate_Invalid_Future(){
        LocalDate incidentDate = LocalDate.now().plusDays(1);
        assertThrows(CannotReportIncidentException.class,
                () -> {
                    incidentsService.validateReportDate(incidentDate);
                });
    }

    @Test
    void should_throw_InvalidDateException_When_IncidentDate_Invalid_Past(){
        LocalDate incidentDate = LocalDate.now().minusDays(3);
        assertThrows(CannotReportIncidentException.class,
                () -> {
                    incidentsService.validateReportDate(incidentDate);
                });
    }

    @Test
    void should_throw_IdNotFoundException_When_BookingId_Exists(){
        int bookingId = 16157;
        when(incidentsRepository.existsByBookingId(bookingId)).thenReturn(true);
        assertThrows(IdFoundException.class, () -> incidentsService.validateBookingId(bookingId));
    }

    @Test
    void should_find_all_pending_incidents(){
        List<Incidents> incidentsList = new ArrayList<>();
        Incidents incident = new Incidents(
                "2024-2375",
                LocalDate.now(),
                LocalDate.now(),
                20,
                LocalDate.now(),
                40,
                "summary",
                "details",
                2232,
                IncidentStatusValue.valueOf("PENDING"),
                null
        );
        Incidents incident1 = new Incidents(
                "2024-2376",
                LocalDate.now(),
                LocalDate.now(),
                21,
                LocalDate.now(),
                41,
                "summary",
                "details",
                2233,
                IncidentStatusValue.valueOf("CLOSED"),
                null
        );

        incidentsList.add(incident);
        incidentsList.add(incident1);

        when(incidentsRepository.findByStatus(IncidentStatusValue.PENDING)).thenReturn(incidentsList);
        when(incidentsDTOMapper.toIncidentDTO(any(Incidents.class)))
                .thenReturn(
                        new IncidentsDTO(
                                "2024-2375",
                                LocalDate.now(),
                                LocalDate.now(),
                                20,
                                LocalDate.now(),
                                40,
                                "summary",
                                "details",
                                2232,
                                IncidentStatusValue.valueOf("PENDING"),
                                0,
                                0
                        )
                );
        List<IncidentsDTO> incidentsDTOList = incidentsService.getAllPendingIncidents();

        verify(incidentsRepository, times(1)).findByStatus(IncidentStatusValue.PENDING);
        assertNotNull(incidentsDTOList);
        assertFalse(incidentsDTOList.isEmpty());
        assertEquals(incidentsList.get(0).getIncidentId(), incidentsDTOList.get(0).getIncidentId());
        assertEquals(incidentsList.get(0).getStatus(), incidentsDTOList.get(0).getStatus());
        assertNotEquals(incidentsList.get(1).getStatus(), incidentsDTOList.get(0).getStatus());
    }

    @Test
    void testUpdateAnIncident(){
        String incidentId = "2023-1433";

        InvestigationReportDTO investigationReportDTO = new InvestigationReportDTO();
        investigationReportDTO.setFindings("resolved");
        investigationReportDTO.setSuggestions("suggestions");

        InvestigationReportDTO newInvestigationReportDTO = new InvestigationReportDTO();
        newInvestigationReportDTO.setFindings("resolved");
        newInvestigationReportDTO.setSuggestions("suggestions");

        when(investigationDetailsServiceImpl.createInvestigationReport(investigationReportDTO)).thenReturn(newInvestigationReportDTO);

        Incidents incidents = new Incidents();
        incidents.setIncidentId(incidentId);
        incidents.setIncidentSummary("summary");
        incidents.setStatus(IncidentStatusValue.valueOf("PENDING"));

        when(incidentsRepository.findById(incidentId)).thenReturn(Optional.of(incidents));

        IncidentsDTO incidentsDTO = new IncidentsDTO();
        incidentsDTO.setIncidentSummary("summary");
        incidentsDTO.setStatus(IncidentStatusValue.valueOf("PENDING"));
        incidentsDTO.setIncidentId(incidentId);

        when(incidentsDTOMapper.toIncidentDTO(incidents)).thenReturn(incidentsDTO);

        Incidents mappedIncidents = new Incidents();

        mappedIncidents.setIncidentSummary("summary");
        mappedIncidents.setStatus(IncidentStatusValue.valueOf("CLOSED"));
        mappedIncidents.setIncidentId(incidentId);

        incidentsDTO.setStatus(IncidentStatusValue.valueOf("CLOSED"));

        when(incidentsDTOMapper.toIncidents(incidentsDTO)).thenReturn(mappedIncidents);

        Incidents savedIncidents = new Incidents();

        savedIncidents.setIncidentSummary("summary");
        savedIncidents.setStatus(IncidentStatusValue.valueOf("CLOSED"));
        savedIncidents.setIncidentId(incidentId);

        when(incidentsRepository.save(mappedIncidents)).thenReturn(savedIncidents);

        InvestigationReportDTO investigationReport = incidentsService.updateAnIncident(incidentId, investigationReportDTO);

        verify(incidentsRepository, times(1)).findById(incidentId);
        verify(incidentsRepository, times(1)).save(mappedIncidents);
        verify(investigationDetailsServiceImpl, times(1)).createInvestigationReport(investigationReportDTO);

        assertTrue(investigationReportDTO.getFindings().contains("resolved"));
        assertEquals(investigationReportDTO.getFindings(), investigationReport.getFindings());
        assertEquals(incidentsDTO.getStatus(), savedIncidents.getStatus());
        assertEquals(investigationReportDTO.getFindings(), newInvestigationReportDTO.getFindings());
    }

    @Test
    void should_return_true_if_findingIsResolved(){
        String message = "This case is resolved";
        boolean isTrue = incidentsService.findIfResolvedOrNot(message);
        assertTrue(isTrue);
    }

    @Test
    void should_return_false_if_findingIsUnResolved(){
        String message = "This case is unresolved";

        boolean isFalse = incidentsService.findIfResolvedOrNot(message);

        assertFalse(isFalse);
    }

    @Test
    void should_throw_NullPointerException_when_InvestigationReport_IsNull(){
        String incidentId = "2024-1542";
        when(incidentsRepository.findById(incidentId)).thenReturn(Optional.empty());

        assertThrows(NullPointerException.class,
                () ->
                    incidentsService.updateAnIncident(incidentId, null)
                );
    }

    @Test
    void should_throw_IdNotFoundException_when_IncidentID_Invalid(){
        String incidentId = "2024-1542";
        when(incidentsRepository.findById(incidentId)).thenReturn(Optional.empty());
        InvestigationReportDTO investigationReportDTO = new InvestigationReportDTO();
        assertThrows(IdNotFoundException.class,
                () -> {
                    incidentsService.updateAnIncident(incidentId, investigationReportDTO);
                }
        );
    }
}