package com.example.flighttracker2.Specification;

import com.example.flighttracker2.Enum.FlightStatus;
import com.example.flighttracker2.Model.Flight;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class FlightSpecification {
    public static Specification<Flight> searchFlights(
            String flightNumber,
            String airlineName,
            String departureAirport,
            String arrivalAirport,
            LocalDateTime startDate,
            LocalDateTime endDate,
            FlightStatus status) {

        return (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (flightNumber != null && !flightNumber.isEmpty()) {
                predicates.add(criteriaBuilder.like(
                        criteriaBuilder.lower(root.get("flightNumber")),
                        "%" + flightNumber.toLowerCase() + "%"
                ));
            }

            if (airlineName != null && !airlineName.isEmpty()) {
                predicates.add(criteriaBuilder.like(
                        criteriaBuilder.lower(root.get("airline").get("name")),
                        "%" + airlineName.toLowerCase() + "%"
                ));
            }

            if (departureAirport != null && !departureAirport.isEmpty()) {
                predicates.add(criteriaBuilder.like(
                        criteriaBuilder.lower(root.get("departureAirport").get("code")),
                        "%" + departureAirport.toLowerCase() + "%"
                ));
            }

            if (arrivalAirport != null && !arrivalAirport.isEmpty()) {
                predicates.add(criteriaBuilder.like(
                        criteriaBuilder.lower(root.get("arrivalAirport").get("code")),
                        "%" + arrivalAirport.toLowerCase() + "%"
                ));
            }

            if (startDate != null) {
                predicates.add(criteriaBuilder.greaterThanOrEqualTo(
                        root.get("scheduledDeparture"), startDate
                ));
            }

            if (endDate != null) {
                predicates.add(criteriaBuilder.lessThanOrEqualTo(
                        root.get("scheduledDeparture"), endDate
                ));
            }

            if (status != null) {
                predicates.add(criteriaBuilder.equal(
                        root.get("status"), status
                ));
            }

            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
    }
}