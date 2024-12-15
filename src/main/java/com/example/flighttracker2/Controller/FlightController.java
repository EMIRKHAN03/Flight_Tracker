package com.example.flighttracker2.Controller;

import com.example.flighttracker2.Enum.FlightStatus;
import com.example.flighttracker2.Model.Flight;
import com.example.flighttracker2.Service.AirlineService;
import com.example.flighttracker2.Service.AirportService;
import com.example.flighttracker2.Service.FlightService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import org.springframework.data.web.PageableDefault;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;


import java.time.LocalDateTime;

@Controller
@RequestMapping("/flights")
@RequiredArgsConstructor
public class FlightController {
    private final FlightService flightService;
    private final AirlineService airlineService;
    private final AirportService airportService;

    @GetMapping
    public String listFlights(Model model) {
        model.addAttribute("flights", flightService.findAll());
        return "flight/list";
    }

    @GetMapping("/create")
    public String createFlightForm(Model model) {
        model.addAttribute("flight", new Flight());
        model.addAttribute("airlines", airlineService.findAll());
        model.addAttribute("airports", airportService.findAll());
        model.addAttribute("statuses", FlightStatus.values());
        return "flight/form";
    }

    @PostMapping
    public String saveFlight(@Valid @ModelAttribute Flight flight,
                             BindingResult bindingResult,
                             RedirectAttributes redirectAttributes,
                             Model model) {
        // Additional custom validation
        if (flight.getDepartureAirport() != null && flight.getArrivalAirport() != null &&
                flight.getDepartureAirport().getId().equals(flight.getArrivalAirport().getId())) {
            bindingResult.rejectValue("arrivalAirport", "error.flight", "Departure and arrival airports cannot be the same");
        }

        if (bindingResult.hasErrors()) {
            model.addAttribute("airlines", airlineService.findAll());
            model.addAttribute("airports", airportService.findAll());
            model.addAttribute("statuses", FlightStatus.values());
            model.addAttribute("org.springframework.validation.BindingResult.flight", bindingResult);
            return "flight/form";
        }

        try {
            flightService.save(flight);
            redirectAttributes.addFlashAttribute("successMessage", "Flight saved successfully");
            return "redirect:/flights";
        } catch (Exception e) {
            model.addAttribute("airlines", airlineService.findAll());
            model.addAttribute("airports", airportService.findAll());
            model.addAttribute("statuses", FlightStatus.values());
            bindingResult.rejectValue("flightNumber", "error.flight", "Flight number must be unique");
            model.addAttribute("org.springframework.validation.BindingResult.flight", bindingResult);
            return "flight/form";
        }
    }

    @GetMapping("/edit/{id}")
    public String editFlightForm(@PathVariable Long id, Model model) {
        model.addAttribute("flight", flightService.findById(id));
        model.addAttribute("airlines", airlineService.findAll());
        model.addAttribute("airports", airportService.findAll());
        model.addAttribute("statuses", FlightStatus.values());
        return "flight/form";
    }

    @GetMapping("/delete/{id}")
    public String deleteFlight(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        flightService.deleteById(id);
        redirectAttributes.addFlashAttribute("successMessage", "Flight deleted successfully");
        return "redirect:/flights";
    }


    @GetMapping("/search")
    public String searchFlights(
            @RequestParam(required = false) String flightNumber,
            @RequestParam(required = false) String airlineName,
            @RequestParam(required = false) String departureAirport,
            @RequestParam(required = false) String arrivalAirport,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startDate,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endDate,
            @RequestParam(required = false) FlightStatus status,
            @PageableDefault(size = 10) Pageable pageable,  // Убрать приведение типов
            Model model
    ) {
        Page<Flight> flightPage = flightService.searchFlights(
                flightNumber,
                airlineName,
                departureAirport,
                arrivalAirport,
                startDate,
                endDate,
                status,
                pageable
        );

        model.addAttribute("flights", flightPage.getContent());
        model.addAttribute("page", flightPage);
        model.addAttribute("flightNumber", flightNumber);
        model.addAttribute("airlineName", airlineName);
        model.addAttribute("departureAirport", departureAirport);
        model.addAttribute("arrivalAirport", arrivalAirport);
        model.addAttribute("startDate", startDate);
        model.addAttribute("endDate", endDate);
        model.addAttribute("status", status);

        return "flight/search";
    }
}