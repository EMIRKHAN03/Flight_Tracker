package com.example.flighttracker2.Model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
@Entity
@Table(name = "airlines")
public class Airline {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Airline code is required")
    @Size(min = 2, max = 5, message = "Airline code must be between 2 and 5 characters")
    @Column(nullable = false, unique = true)
    private String code;

    @NotBlank(message = "Airline name is required")
    @Size(min = 2, max = 100, message = "Airline name must be between 2 and 100 characters")
    @Column(nullable = false)
    private String name;

    @Size(max = 100, message = "Country name must be less than 100 characters")
    private String country;
}