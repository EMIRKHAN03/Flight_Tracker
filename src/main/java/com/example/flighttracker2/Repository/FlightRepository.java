package com.example.flighttracker2.Repository;


import com.example.flighttracker2.Enum.FlightStatus;
import com.example.flighttracker2.Model.Flight;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.time.LocalDateTime;
import java.util.List;

public interface FlightRepository extends JpaRepository<Flight, Long>, JpaSpecificationExecutor<Flight> {
    List<Flight> findByFlightNumber(String flightNumber);
    List<Flight> findByStatus(FlightStatus status);
    List<Flight> findByScheduledDepartureBetween(LocalDateTime start, LocalDateTime end);

}
