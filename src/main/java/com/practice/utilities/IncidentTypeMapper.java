package com.practice.utilities;

import com.practice.dtos.IncidentTypeDTO;
import com.practice.entities.IncidentTypes;
import org.springframework.stereotype.Service;

@Service
public class IncidentTypeMapper {

    public IncidentTypeDTO toIncidentTypeDTO(IncidentTypes incidentTypes){
        if(incidentTypes == null){
            throw new NullPointerException("Incident Type Should Not Be Null");
        }
        IncidentTypeDTO incidentTypeDTO = new IncidentTypeDTO();

        incidentTypeDTO.setIncidentTypeId(incidentTypes.getIncidentTypeId());
        incidentTypeDTO.setType(incidentTypes.getType());
        incidentTypeDTO.setExpectedSLAInDays(incidentTypes.getExpectedSLAInDays());

        return incidentTypeDTO;
    }
}
