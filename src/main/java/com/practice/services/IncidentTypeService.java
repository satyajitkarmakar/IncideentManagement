package com.practice.services;

import com.practice.dtos.IncidentTypeDTO;

import java.util.List;

public interface IncidentTypeService {

    // this method will return a list of incident type present in  the database
    List<IncidentTypeDTO> getAllIncidentTypes();
}
