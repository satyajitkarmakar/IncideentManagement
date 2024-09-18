package com.practice.repositories.test;

import com.practice.entities.IncidentTypes;
import com.practice.entities.Incidents;
import com.practice.repositories.IncidentsRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class IncidentsRepositoryTest {

    @Autowired
    private TestEntityManager testEntityManager;

    @Autowired
    private IncidentsRepository incidentsRepository;

    @Test
    void should_find_all_the_incidents(){
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
        List<Incidents> incident = incidentsRepository.findAll();
        assertFalse(incident.isEmpty());
        assertEquals(incidentTypes.getIncidentTypeId(), incident.get(0).getIncidentTypes().getIncidentTypeId());
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

        Optional<Incidents> incident = incidentsRepository.findById("2023-1264");
        assertTrue(incident.isPresent());
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

        Optional<Incidents> incident = incidentsRepository.findById("2023-1294");
        assertFalse(incident.isPresent());
    }

    @Test
    void should_save_a_incident(){
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
        incidentsRepository.save(incidents);
        Optional<Incidents> incident = incidentsRepository.findById("2023-1264");
        assertTrue(incident.isPresent());
    }

    @Test
    void should_delete_incident_by_id(){
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
        incidentsRepository.deleteById("2023-1264");
        Optional<Incidents> incident = incidentsRepository.findById("2023-1264");
        assertFalse(incident.isPresent());
    }

}