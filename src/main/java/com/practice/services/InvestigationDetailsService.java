package com.practice.services;

import com.practice.dtos.InvestigationReportDTO;

import java.util.List;

public interface InvestigationDetailsService {
    InvestigationReportDTO createInvestigationReport(InvestigationReportDTO investigationReportDTO);

    List<InvestigationReportDTO> getAllInvestigationReport();
}
