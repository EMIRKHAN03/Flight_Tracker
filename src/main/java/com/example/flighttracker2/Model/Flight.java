package com.example.flighttracker2.Model;

import com.example.flighttracker2.Enum.FlightStatus;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "flights")
public class Flight {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Flight number is required")
    @Pattern(regexp = "^[A-Z]{2}\\d{3,4}$", message = "Flight number must be in format like AA123 or AA1234")
    @Column(nullable = false)
    private String flightNumber;

    @NotNull(message = "Airline is required")
    @ManyToOne
    @JoinColumn(name = "airline_id", nullable = false)
    private Airline airline;

    @NotNull(message = "Departure airport is required")
    @ManyToOne
    @JoinColumn(name = "departure_airport_id", nullable = false)
    private Airport departureAirport;

    @NotNull(message = "Arrival airport is required")
    @ManyToOne
    @JoinColumn(name = "arrival_airport_id", nullable = false)
    private Airport arrivalAirport;

    @NotNull(message = "Scheduled departure is required")
    @Future(message = "Scheduled departure must be in the future")
    private LocalDateTime scheduledDeparture;

    @NotNull(message = "Scheduled arrival is required")
    @Future(message = "Scheduled arrival must be in the future")
    private LocalDateTime scheduledArrival;

    private LocalDateTime actualDeparture;
    private LocalDateTime actualArrival;

    @NotNull(message = "Flight status is required")
    @Enumerated(EnumType.STRING)
    private FlightStatus status;

    @Size(max = 10, message = "Terminal must be less than 10 characters")
    private String terminal;

    @Size(max = 10, message = "Gate must be less than 10 characters")
    private String gate;

    // Custom validation to ensure arrival is after departure
    @AssertTrue(message = "Scheduled arrival must be after scheduled departure")
    public boolean isValidScheduleDates() {
        return scheduledDeparture == null || scheduledArrival == null ||
                scheduledArrival.isAfter(scheduledDeparture);
    }
}
