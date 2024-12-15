package com.example.flighttracker2.Service;


import com.example.flighttracker2.Model.Airport;
import com.example.flighttracker2.Repository.AirportRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AirportService {
    private final AirportRepository airportRepository;

    public List<Airport> findAll() {
        return airportRepository.findAll();
    }

    public Airport save(Airport airport) {
        return airportRepository.save(airport);
    }

    public Airport findById(Long id) {
        return airportRepository.findById(id).orElse(null);
    }

    public void deleteById(Long id) {
        airportRepository.deleteById(id);
    }
}