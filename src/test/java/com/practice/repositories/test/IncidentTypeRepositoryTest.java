package com.practice.repositories.test;

import com.practice.entities.IncidentTypes;
import com.practice.repositories.IncidentTypeRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class IncidentTypeRepositoryTest {

    @Autowired
    private TestEntityManager testEntityManager;

    @Autowired
    private IncidentTypeRepository incidentTypeRepository;

    @Test
    void should_find_all_the_incident_types(){
        IncidentTypes incidentTypes = new IncidentTypes();

        incidentTypes.setIncidentTypeId(1);
        incidentTypes.setType(2);
        incidentTypes.setExpectedSLAInDays(10);

        testEntityManager.persist(incidentTypes);

        List<IncidentTypes> incidentTypesList = incidentTypeRepository.findAll();

        assertFalse(incidentTypesList.isEmpty());
    }

    @Test
    void should_find_incident_type_by_given_id(){
        IncidentTypes incidentTypes = new IncidentTypes();

        incidentTypes.setIncidentTypeId(1);
        incidentTypes.setType(2);
        incidentTypes.setExpectedSLAInDays(10);

        testEntityManager.persist(incidentTypes);

        Optional<IncidentTypes> incidentTypesList = incidentTypeRepository.findById(1);

        assertTrue(incidentTypesList.isPresent());
    }
    @Test
    void should_not_the_incident_type_by_id(){
        IncidentTypes incidentTypes = new IncidentTypes();

        incidentTypes.setIncidentTypeId(1);
        incidentTypes.setType(2);
        incidentTypes.setExpectedSLAInDays(10);

        testEntityManager.persist(incidentTypes);

        Optional<IncidentTypes> incidentTypesList = incidentTypeRepository.findById(2);

        assertFalse(incidentTypesList.isPresent());
    }

    @Test
    void should_save_a_incident_type(){
        IncidentTypes incidentTypes = new IncidentTypes();

        incidentTypes.setIncidentTypeId(1);
        incidentTypes.setType(2);
        incidentTypes.setExpectedSLAInDays(10);

        testEntityManager.persist(incidentTypes);

        Optional<IncidentTypes> incidentTypesList = incidentTypeRepository.findById(1);

        assertTrue(incidentTypesList.isPresent());
    }

    @Test
    void should_delete_incident_type_by_id(){
        IncidentTypes incidentTypes = new IncidentTypes();

        incidentTypes.setIncidentTypeId(1);
        incidentTypes.setType(2);
        incidentTypes.setExpectedSLAInDays(10);

        testEntityManager.persist(incidentTypes);
        incidentTypeRepository.deleteById(1);

        Optional<IncidentTypes> incidentTypesList = incidentTypeRepository.findById(1);

        assertFalse(incidentTypesList.isPresent());
    }

}