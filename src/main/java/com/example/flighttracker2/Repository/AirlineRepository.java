package com.example.flighttracker2.Repository;


import com.example.flighttracker2.Model.Airline;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AirlineRepository extends JpaRepository<Airline, Long> {
    Airline findByCode(String code);
}