package com.practice.controllers.test;

import com.practice.controllers.IncidentTypeController;
import com.practice.dtos.IncidentTypeDTO;
import com.practice.services.IncidentTypeService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


class IncidentTypeControllerTest {

    private MockMvc mockMvc;
    @InjectMocks
    private IncidentTypeController incidentTypeController;

    @Mock
    private IncidentTypeService incidentTypeService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc= MockMvcBuilders.standaloneSetup(incidentTypeController).build();
    }

    @Test
    void should_find_all_incident_type(){
        List<IncidentTypeDTO> dtoList = new ArrayList<>();
        IncidentTypeDTO incidentTypeDTO = new IncidentTypeDTO(
                20007,
                6,
                12
        );

        dtoList.add(incidentTypeDTO);
        when(incidentTypeService.getAllIncidentTypes()).thenReturn(dtoList);

        ResponseEntity<?> responseEntity = incidentTypeController.findAllIncidentTypes();
        List<IncidentTypeDTO> actualList = (List<IncidentTypeDTO>) responseEntity.getBody();

        assertTrue(actualList.size() > 0);
    }

    @Test
    void testUri_Should_GetAllIncidentType(){
        List<IncidentTypeDTO> dtoList = new ArrayList<>();
        IncidentTypeDTO incidentTypeDTO = new IncidentTypeDTO(
                20007,
                6,
                12
        );

        dtoList.add(incidentTypeDTO);
        when(incidentTypeService.getAllIncidentTypes()).thenReturn(dtoList);

        ResponseEntity<?> responseEntity = incidentTypeController.findAllIncidentTypes();
        try{
            MvcResult result = mockMvc.perform(get("http://localhost:8089/api/incidents/types"))
                    .andExpect(status().isOk())
                    .andReturn();
        }catch(Exception e){
            assertTrue(false);
        }
    }

    @Test
    void testUri_Should_Not_GetAllIncidentType(){
        List<IncidentTypeDTO> dtoList = new ArrayList<>();
        IncidentTypeDTO incidentTypeDTO = new IncidentTypeDTO(
                20007,
                6,
                12
        );

        dtoList.add(incidentTypeDTO);
        when(incidentTypeService.getAllIncidentTypes()).thenReturn(dtoList);

        ResponseEntity<?> responseEntity = incidentTypeController.findAllIncidentTypes();
        try{
            MvcResult result = mockMvc.perform(get("http://localhost:8090/api/incidents/type"))
                    .andExpect(status().isNotFound())
                    .andReturn();
        }catch(Exception e){
            assertFalse(true);
        }
    }
}