package com.example.flighttracker2.Service;

import com.example.flighttracker2.Enum.FlightStatus;
import com.example.flighttracker2.Model.Flight;
import com.example.flighttracker2.Repository.FlightRepository;
import com.example.flighttracker2.Specification.FlightSpecification;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class FlightService {
    private final FlightRepository flightRepository;

    public List<Flight> findAll() {
        return flightRepository.findAll();
    }

    public Flight save(Flight flight) {
        return flightRepository.save(flight);
    }

    public Flight findById(Long id) {
        return flightRepository.findById(id).orElse(null);
    }

    public void deleteById(Long id) {
        flightRepository.deleteById(id);
    }

    public List<Flight> findByStatus(FlightStatus status) {
        return flightRepository.findByStatus(status);
    }

    public List<Flight> findByDateRange(LocalDateTime start, LocalDateTime end) {
        return flightRepository.findByScheduledDepartureBetween(start, end);
    }



    public Page<Flight> searchFlights(
            String flightNumber,
            String airlineName,
            String departureAirport,
            String arrivalAirport,
            LocalDateTime startDate,
            LocalDateTime endDate,
            FlightStatus status,
            Pageable pageable) {

        return flightRepository.findAll(
                FlightSpecification.searchFlights(
                        flightNumber,
                        airlineName,
                        departureAirport,
                        arrivalAirport,
                        startDate,
                        endDate,
                        status
                ), pageable
        );
    }
}

