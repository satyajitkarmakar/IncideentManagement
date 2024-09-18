package com.practice.services.impls;

import com.practice.dtos.InvestigationReportDTO;
import com.practice.entities.Incidents;
import com.practice.entities.InvestigationDetails;
import com.practice.exceptions.IdNotFoundException;
import com.practice.exceptions.InvalidDateException;
import com.practice.repositories.IncidentsRepository;
import com.practice.repositories.InvestigationDetailsRepository;
import com.practice.services.InvestigationDetailsService;
import com.practice.utilities.InvestigationReportMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class InvestigationDetailsServiceImpl implements InvestigationDetailsService {

    private final IncidentsRepository incidentsRepository;

    private final InvestigationReportMapper investigationReportMapper;

    private final InvestigationDetailsRepository investigationDetailsRepository;
    @Autowired
    public InvestigationDetailsServiceImpl( IncidentsRepository incidentsRepository,InvestigationReportMapper investigationReportMapper,
    InvestigationDetailsRepository investigationDetailsRepository){
        this.incidentsRepository = incidentsRepository;
        this.investigationReportMapper = investigationReportMapper;
        this.investigationDetailsRepository = investigationDetailsRepository;
    }

    //this method is used to create an investigation report
    @Override
    public InvestigationReportDTO createInvestigationReport(InvestigationReportDTO investigationReportDTO){
        if(investigationReportDTO == null){
            throw new NullPointerException("Investigation Report should not be null.");
        }

//        validateInvestigationDate(investigationReportDTO.getInvestigationDate());

        //finding an incident based on an id
        Optional<Incidents> incident = incidentsRepository.findById(investigationReportDTO.getIncidentId());
        InvestigationReportDTO createInvestigationReport;

        //if the incident is present then we are creating an investigation report
        if(incident.isPresent()){
            investigationReportDTO.setInvestigationDetailsId((int)createInvestigationDetailsId());

            //this is used to convert investigation dto to investigation entity
            InvestigationDetails investigationDetails = investigationReportMapper.toInvestigationDetails(investigationReportDTO);

            //now we are saving the investigation report in the db
            InvestigationDetails createInvestigationDetails = investigationDetailsRepository.save(investigationDetails);

            createInvestigationReport = investigationReportMapper.toInvestigationReport(createInvestigationDetails);
        }
        else{
            throw new IdNotFoundException("Incident ID is invalid");
        }
        return createInvestigationReport;
    }

    @Override
    public List<InvestigationReportDTO> getAllInvestigationReport(){
        return investigationDetailsRepository.findAll()
                .stream()
                .map(investigationReportMapper::toInvestigationReport)
                .toList();
    }
    public long createInvestigationDetailsId(){
        UUID uuid = UUID.randomUUID();
        long leastSignificantBits = uuid.getLeastSignificantBits();
        return (Math.abs(leastSignificantBits) % 9000000) + 1000000L;
    }

    public void validateInvestigationDate(LocalDate investigationDate) {
        if (investigationDate.isAfter(LocalDate.now()) || investigationDate.isBefore(LocalDate.now())) {
            throw new InvalidDateException("Investigation date should be current date");
        }
    }
}
