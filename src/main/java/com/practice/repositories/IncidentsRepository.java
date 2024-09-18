package com.practice.repositories;

import com.practice.dtos.IncidentStatusValue;
import com.practice.entities.Incidents;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface IncidentsRepository extends JpaRepository<Incidents, String> {

    /*this method will return true if a booking id is already present in the database
    otherwise it will return false.*/

    boolean existsByBookingId(int bookingId);

    /*If there is present any pending incident then this method will return
    all the pending incidents.*/
    List<Incidents> findByStatus(IncidentStatusValue status);
}
