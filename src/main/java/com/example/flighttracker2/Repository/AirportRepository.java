package com.example.flighttracker2.Repository;


import com.example.flighttracker2.Model.Airport;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AirportRepository extends JpaRepository<Airport, Long> {
    Airport findByCode(String code);
}
