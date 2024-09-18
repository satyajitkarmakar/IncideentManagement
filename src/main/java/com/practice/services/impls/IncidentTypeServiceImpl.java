package com.practice.services.impls;

import com.practice.dtos.IncidentTypeDTO;
import com.practice.entities.IncidentTypes;
import com.practice.repositories.IncidentTypeRepository;
import com.practice.services.IncidentTypeService;
import com.practice.utilities.IncidentTypeMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class IncidentTypeServiceImpl implements IncidentTypeService {
    private final IncidentTypeRepository incidentTypeRepository;
    private  final IncidentTypeMapper incidentTypeMapper;
    @Autowired
    public IncidentTypeServiceImpl(IncidentTypeRepository incidentTypeRepository,IncidentTypeMapper incidentTypeMapper){
        this.incidentTypeRepository=incidentTypeRepository;
        this.incidentTypeMapper=incidentTypeMapper;
    }

    /**
     *
     * @return
     */
    @Override
    public List<IncidentTypeDTO> getAllIncidentTypes() {
        //using find all method we are finding all incident types that are present in the system
        List<IncidentTypes> incidentTypesList = incidentTypeRepository.findAll();

        //we are converting incident type entity to incident type dto
        //using map method, then we are converting it into list
        return incidentTypesList.stream()
                .map(incidentTypeMapper::toIncidentTypeDTO)
                .toList();
    }
}
