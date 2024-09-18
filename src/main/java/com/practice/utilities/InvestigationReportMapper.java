package com.practice.utilities;

import com.practice.dtos.InvestigationReportDTO;
import com.practice.entities.Incidents;
import com.practice.entities.InvestigationDetails;
import org.springframework.stereotype.Service;

@Service
public class InvestigationReportMapper {

    public InvestigationDetails toInvestigationDetails(InvestigationReportDTO investigationReportDTO){
        InvestigationDetails investigationDetails = new InvestigationDetails();

        investigationDetails.setInvestigationDetailsId(investigationReportDTO.getInvestigationDetailsId());
        investigationDetails.setFindings(investigationReportDTO.getFindings());
        investigationDetails.setSuggestions(investigationReportDTO.getSuggestions());
        investigationDetails.setInvestigationDate(investigationReportDTO.getInvestigationDate());

        Incidents incidents = new Incidents();
        incidents.setIncidentId(investigationReportDTO.getIncidentId());
        investigationDetails.setIncidents(incidents);

        return investigationDetails;
    }

    public InvestigationReportDTO toInvestigationReport(InvestigationDetails investigationDetails){
        return new InvestigationReportDTO(
                investigationDetails.getInvestigationDetailsId(),
                investigationDetails.getFindings(),
                investigationDetails.getSuggestions(),
                investigationDetails.getInvestigationDate(),
                investigationDetails.getIncidents().getIncidentId()
        );
    }
}
