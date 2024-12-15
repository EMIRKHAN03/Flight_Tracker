package com.example.flighttracker2.Service;


import com.example.flighttracker2.Model.Airline;
import com.example.flighttracker2.Repository.AirlineRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AirlineService {
    private final AirlineRepository airlineRepository;

    public List<Airline> findAll() {
        return airlineRepository.findAll();
    }

    public Airline save(Airline airline) {
        return airlineRepository.save(airline);
    }

    public Airline findById(Long id) {
        return airlineRepository.findById(id).orElse(null);
    }

    public void deleteById(Long id) {
        airlineRepository.deleteById(id);
    }
}
