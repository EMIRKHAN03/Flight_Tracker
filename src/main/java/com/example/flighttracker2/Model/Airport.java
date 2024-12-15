
package com.example.flighttracker2.Model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
@Entity
@Table(name = "airports")
public class Airport {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Airport code is required")
    @Size(min = 3, max = 5, message = "Airport code must be between 3 and 5 characters")
    @Column(nullable = false, unique = true)
    private String code;

    @NotBlank(message = "Airport name is required")
    @Size(min = 2, max = 100, message = "Airport name must be between 2 and 100 characters")
    @Column(nullable = false)
    private String name;

    @Size(max = 100, message = "City name must be less than 100 characters")
    private String city;

    @Size(max = 100, message = "Country name must be less than 100 characters")
    private String country;
}
