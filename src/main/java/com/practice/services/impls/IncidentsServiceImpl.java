package com.practice.services.impls;

import com.practice.dtos.IncidentStatusValue;
import com.practice.dtos.IncidentsDTO;
import com.practice.dtos.InvestigationReportDTO;
import com.practice.dtos.NewIncidentDTO;
import com.practice.entities.IncidentTypes;
import com.practice.entities.Incidents;
import com.practice.exceptions.CannotReportIncidentException;
import com.practice.exceptions.IdFoundException;
import com.practice.exceptions.IdNotFoundException;
import com.practice.repositories.IncidentTypeRepository;
import com.practice.repositories.IncidentsRepository;
import com.practice.services.IncidentsService;
import com.practice.utilities.IncidentsDTOMapper;
import com.practice.utilities.NewIncidentDTOMapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.*;

@Service
public class IncidentsServiceImpl implements IncidentsService {
    private int investigationId = 0;
    private final NewIncidentDTOMapper newIncidentDTOMapper;
    private final IncidentsRepository incidentsRepository;
    private final IncidentTypeRepository incidentTypeRepository;
    private final IncidentsDTOMapper incidentsDTOMapper;
    private final InvestigationDetailsServiceImpl investigationDetailsServiceImpl;

    @Autowired
    public IncidentsServiceImpl(NewIncidentDTOMapper newIncidentDTOMapper, IncidentsRepository incidentsRepository,
                                IncidentTypeRepository incidentTypeRepository,
                                IncidentsDTOMapper incidentsDTOMapper, InvestigationDetailsServiceImpl investigationDetailsServiceImpl) {
        this.newIncidentDTOMapper = newIncidentDTOMapper;
        this.incidentsRepository = incidentsRepository;
        this.incidentTypeRepository = incidentTypeRepository;
        this.incidentsDTOMapper = incidentsDTOMapper;
        this.investigationDetailsServiceImpl = investigationDetailsServiceImpl;

    }



    public NewIncidentDTO createNewIncident(NewIncidentDTO newIncidentDTO) {

        validateIncidentDate(newIncidentDTO.getIncidentDate());

        //This method will validate the report date if it is within two days
        //of incident date or not
        validateReportDate(newIncidentDTO.getReportDate());

        //this method will validate if a booking id for an incident report is present or not
        validateBookingId(newIncidentDTO.getBookingId());


        //using an id we are finding a specific incident type object
        IncidentTypes incidentType = incidentTypeRepository.findById(newIncidentDTO.getIncidentTypeId())
                .orElseThrow(() -> new IdNotFoundException("Invalid IncidentType ID"));

        //base on incident date we are generating an incident id
        newIncidentDTO.setIncidentId(createCustomIncidentId(newIncidentDTO.getIncidentDate()));

        //resolution eta is calculated using an incident type id and expected time it will to be resolved,
        //from the report date.
        newIncidentDTO.setResolutionETA(newIncidentDTO.getReportDate().plusDays(incidentType.getExpectedSLAInDays()));

        newIncidentDTO.setIncidentReportedByUserId((int) createUserId());
        newIncidentDTO.setBookingId(createBookingId());

        //default value of all incident report will be pending
        newIncidentDTO.setStatus(IncidentStatusValue.PENDING);

        //using this method we are converting incidentDTO to incident object
        Incidents mappedIncidents = newIncidentDTOMapper.toIncidents(newIncidentDTO);

        //we are storing an incident report in the database
        Incidents saveIncidents = incidentsRepository.save(mappedIncidents);
        return newIncidentDTOMapper.toNewIncidentDTO(saveIncidents);
    }



    @Override
    public IncidentsDTO findAnIncidentById(String incidentId) {
        //using findById we are finding if incident id is present or not
        Optional<Incidents> incidents = incidentsRepository.findById(incidentId);

        IncidentsDTO incidentsDTO;

        if (incidents.isPresent()) {
            //if the given incident is present then we are converting the incident
            //object into incidentDTO object
            incidentsDTO = incidentsDTOMapper.toIncidentDTO(incidents.get());
            incidentsDTO.setInvestigationDetailsId(investigationId);
        } else {
            //otherwise if the id is not present, then it will that the given id is not present
            throw new IdNotFoundException("Incident is not found with ID " + incidentId);
        }

        return incidentsDTO;
    }

    @Override
    public List<IncidentsDTO> getAllPendingIncidents() {
        //using findByStatus we are finding all the pending incidents present in the system.
        List<Incidents> pendingIncidentList = incidentsRepository.findByStatus(IncidentStatusValue.PENDING);

        return pendingIncidentList.stream()
                .map(incidentsDTOMapper::toIncidentDTO)
                .toList();
    }



    @Override
    public InvestigationReportDTO updateAnIncident(String incidentId, InvestigationReportDTO investigationReportDTO) {
        if (investigationReportDTO == null) {
            throw new NullPointerException("InvestigationReport should not be null");
        }
        //creating a new investigation report and storing it in the database
        InvestigationReportDTO investigationReport = investigationDetailsServiceImpl.createInvestigationReport(investigationReportDTO);

        //using findById we are finding if incident id is present or not
        Optional<Incidents> incident = incidentsRepository.findById(incidentId);

        boolean isFindingSuccessful;

        if (incident.isPresent()) {
            //if the given incident is present then we are converting the incident
            //object into incidentDTO object
            IncidentsDTO incidentsDTO = incidentsDTOMapper.toIncidentDTO(incident.get());

            //checking weather the incident is resolved or not
            boolean isPresentOrNot = findIfResolvedOrNot(investigationReportDTO.getFindings());

            incidentsDTO.setInvestigatedByUserId((int) createInvestigatorId());

            if (isPresentOrNot) {
                //if the incident is resolved then we are closing the incident
                incidentsDTO.setStatus(IncidentStatusValue.CLOSED);
                investigationId = investigationReportDTO.getInvestigationDetailsId();
                incidentsDTO.setInvestigationDetailsId(investigationId);
                isFindingSuccessful = true;
            } else {
                investigationId = investigationReportDTO.getInvestigationDetailsId();
                incidentsDTO.setInvestigationDetailsId(investigationId);
                isFindingSuccessful = false;
            }

            //using this method we are converting incidentDTO to incident object
            Incidents incidents = incidentsDTOMapper.toIncidents(incidentsDTO);

            //we are storing all the updated values in the database
            incidentsRepository.save(incidents);
            return (isFindingSuccessful) ? investigationReport : null;
        } else {
            //otherwise if the id is not present, then it will that the given id is not present
            throw new IdNotFoundException("Incident is not found with ID " + incidentId);
        }
    }
    public String createCustomIncidentId(LocalDate incidentDate) {
        String customIncidentId = "";

        //first four value will be year of incident
        customIncidentId += incidentDate.getYear();

        //generating 4 digit unique random number
        long uniqueNumber = getRandomUniqueId(9000, 1000);


        return customIncidentId + "-" + uniqueNumber;
    }

    public long createUserId() {
        return getRandomUniqueId(900000, 100000);
    }

    //generating a unique incident booking id
    public int createBookingId() {
        return (int)getRandomUniqueId(90000, 10000);
    }

    //generating a custom investigator id
    public long createInvestigatorId() {
        return getRandomUniqueId(9000, 1);
    }


    public void validateReportDate(LocalDate reportDate) {
        if (reportDate.isAfter(LocalDate.now()) || reportDate.isBefore(LocalDate.now())) {
            throw new CannotReportIncidentException("Report Date should be current date");
        }
    }

    public void validateIncidentDate(LocalDate incidentDate){
        LocalDate currDate = LocalDate.now();
        LocalDate prevDate = LocalDate.now().minusDays(2);
        if(incidentDate.isAfter(currDate) || incidentDate.isBefore(prevDate)){
            throw new CannotReportIncidentException("incident date must be current date or a maximum of 2 days before.");
        }
    }

    public void validateBookingId(int bookingId) {
        //checking weather a booking id is present or not
        if (incidentsRepository.existsByBookingId(bookingId)) {
            throw new IdFoundException("Booking ID is already present");
        }
    }


    public long getRandomUniqueId(int high, int low) {
        UUID uuid = UUID.randomUUID();
        long leastSignificantBits = uuid.getLeastSignificantBits();
        return (Math.abs(leastSignificantBits) % high) + low;
    }


    public boolean findIfResolvedOrNot(String message) {
        String word = "resolved";
        String[] arr = message.split(" ");
        for (String s : arr) {
            if (word.equalsIgnoreCase(s)) {
                return true;
            }
        }
        return false;
    }
}
