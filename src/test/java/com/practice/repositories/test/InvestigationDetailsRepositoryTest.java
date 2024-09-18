package com.practice.repositories.test;

import com.practice.entities.IncidentTypes;
import com.practice.entities.Incidents;
import com.practice.entities.InvestigationDetails;
import com.practice.repositories.InvestigationDetailsRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class InvestigationDetailsRepositoryTest {

    @Autowired
    private InvestigationDetailsRepository investigationDetailsRepository;

    @Autowired
    private TestEntityManager testEntityManager;

    @Test
    void should_find_all_the_investigations(){
        IncidentTypes incidentTypes = new IncidentTypes();
        incidentTypes.setIncidentTypeId(1);
        incidentTypes.setType(2);
        incidentTypes.setExpectedSLAInDays(10);
        testEntityManager.persist(incidentTypes);

        Incidents incidents = new Incidents();
        incidents.setIncidentId("2023-1264");
        incidents.setIncidentDate(LocalDate.now());
        incidents.setReportDate(LocalDate.now());
        incidents.setIncidentTypes(incidentTypes);

        testEntityManager.persist(incidents);

        InvestigationDetails investigationDetails = new InvestigationDetails();

        investigationDetails.setInvestigationDetailsId(10);
        investigationDetails.setIncidents(incidents);

        testEntityManager.persist(investigationDetails);

        List<InvestigationDetails> investigationDetailsList = investigationDetailsRepository.findAll();
        assertNotNull(investigationDetailsList);
        assertEquals(incidents.getIncidentId(), investigationDetailsList.get(0).getIncidents().getIncidentId());
    }

    @Test
    void should_find_incident_by_given_id(){
        IncidentTypes incidentTypes = new IncidentTypes();
        incidentTypes.setIncidentTypeId(1);
        incidentTypes.setType(2);
        incidentTypes.setExpectedSLAInDays(10);
        testEntityManager.persist(incidentTypes);

        Incidents incidents = new Incidents();
        incidents.setIncidentId("2023-1264");
        incidents.setIncidentDate(LocalDate.now());
        incidents.setReportDate(LocalDate.now());
        incidents.setIncidentTypes(incidentTypes);

        testEntityManager.persist(incidents);

        InvestigationDetails investigationDetails = new InvestigationDetails();

        investigationDetails.setInvestigationDetailsId(10);
        investigationDetails.setIncidents(incidents);

        testEntityManager.persist(investigationDetails);
        Optional<InvestigationDetails> investigationDetailsList = investigationDetailsRepository.findById(10);
        assertTrue(investigationDetailsList.isPresent());
    }

    @Test
    void should_not_the_incident_by_id(){
        IncidentTypes incidentTypes = new IncidentTypes();
        incidentTypes.setIncidentTypeId(1);
        incidentTypes.setType(2);
        incidentTypes.setExpectedSLAInDays(10);
        testEntityManager.persist(incidentTypes);

        Incidents incidents = new Incidents();
        incidents.setIncidentId("2023-1264");
        incidents.setIncidentDate(LocalDate.now());
        incidents.setReportDate(LocalDate.now());
        incidents.setIncidentTypes(incidentTypes);

        testEntityManager.persist(incidents);

        InvestigationDetails investigationDetails = new InvestigationDetails();

        investigationDetails.setInvestigationDetailsId(10);
        investigationDetails.setIncidents(incidents);

        testEntityManager.persist(investigationDetails);
        Optional<InvestigationDetails> investigationDetailsList = investigationDetailsRepository.findById(101);
        assertFalse(investigationDetailsList.isPresent());
    }

    @Test
    void should_save_a_investigation(){
        IncidentTypes incidentTypes = new IncidentTypes();
        incidentTypes.setIncidentTypeId(1);
        incidentTypes.setType(2);
        incidentTypes.setExpectedSLAInDays(10);
        testEntityManager.persist(incidentTypes);

        Incidents incidents = new Incidents();
        incidents.setIncidentId("2023-1264");
        incidents.setIncidentDate(LocalDate.now());
        incidents.setReportDate(LocalDate.now());
        incidents.setIncidentTypes(incidentTypes);

        testEntityManager.persist(incidents);

        InvestigationDetails investigationDetails = new InvestigationDetails();

        investigationDetails.setInvestigationDetailsId(10);
        investigationDetails.setIncidents(incidents);

        testEntityManager.persist(investigationDetails);
        investigationDetailsRepository.save(investigationDetails);
        Optional<InvestigationDetails> investigationDetailsList = investigationDetailsRepository.findById(10);
        assertTrue(investigationDetailsList.isPresent());
    }

    @Test
    void should_delete_investigation_by_id(){
        IncidentTypes incidentTypes = new IncidentTypes();
        incidentTypes.setIncidentTypeId(1);
        incidentTypes.setType(2);
        incidentTypes.setExpectedSLAInDays(10);
        testEntityManager.persist(incidentTypes);

        Incidents incidents = new Incidents();
        incidents.setIncidentId("2023-1264");
        incidents.setIncidentDate(LocalDate.now());
        incidents.setReportDate(LocalDate.now());
        incidents.setIncidentTypes(incidentTypes);

        testEntityManager.persist(incidents);

        InvestigationDetails investigationDetails = new InvestigationDetails();

        investigationDetails.setInvestigationDetailsId(10);
        investigationDetails.setIncidents(incidents);
        testEntityManager.persist(investigationDetails);
        investigationDetailsRepository.save(investigationDetails);
        investigationDetailsRepository.deleteById(10);

        Optional<InvestigationDetails> investigationDetailsList = investigationDetailsRepository.findById(10);
        assertFalse(investigationDetailsList.isPresent());
    }
}