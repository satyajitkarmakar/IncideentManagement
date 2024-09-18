package com.practice.repositories;

import com.practice.entities.InvestigationDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InvestigationDetailsRepository extends JpaRepository<InvestigationDetails, Integer> {
}
