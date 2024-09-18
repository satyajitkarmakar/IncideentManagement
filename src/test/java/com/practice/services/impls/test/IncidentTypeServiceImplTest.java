package com.practice.services.impls.test;

import com.practice.dtos.IncidentTypeDTO;
import com.practice.entities.IncidentTypes;
import com.practice.repositories.IncidentTypeRepository;
import com.practice.services.impls.IncidentTypeServiceImpl;
import com.practice.utilities.IncidentTypeMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

class IncidentTypeServiceImplTest {
    @InjectMocks
    private IncidentTypeServiceImpl incidentTypeServiceImpl;
    @Mock
    private IncidentTypeRepository incidentTypeRepository;

    @Mock
    private IncidentTypeMapper incidentTypeMapper;


    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void should_find_all_the_incidents_type(){
        List<IncidentTypes> incidentTypesList = new ArrayList<>();
        IncidentTypes incidentTypes = new IncidentTypes(
                20006,
                6,
                12
        );

        incidentTypesList.add(incidentTypes);

        when(incidentTypeRepository.findAll()).thenReturn(incidentTypesList);
        when(incidentTypeMapper.toIncidentTypeDTO(any(IncidentTypes.class))).thenReturn(
                new IncidentTypeDTO(
                        20006,
                        6,
                        12
                )
        );

        List<IncidentTypeDTO> incidentTypeDTOList = incidentTypeServiceImpl.getAllIncidentTypes();
        verify(incidentTypeRepository,times(1)).findAll();
        assertNotNull(incidentTypeDTOList);
        assertFalse(incidentTypeDTOList.isEmpty());
    }
}