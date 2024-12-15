package com.example.flighttracker2.Controller;

import com.example.flighttracker2.Model.Airport;
import com.example.flighttracker2.Service.AirportService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/airports")
@RequiredArgsConstructor
public class AirportController {
    private final AirportService airportService;

    @GetMapping
    public String listAirports(Model model) {
        model.addAttribute("airports", airportService.findAll());
        return "airport/list";
    }

    @GetMapping("/create")
    public String createAirportForm(Model model) {
        model.addAttribute("airport", new Airport());
        return "airport/form";
    }

    @PostMapping
    public String saveAirport(@Valid @ModelAttribute Airport airport,
                              BindingResult bindingResult,
                              RedirectAttributes redirectAttributes,
                              Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("org.springframework.validation.BindingResult.airport", bindingResult);
            return "airport/form";
        }

        try {
            airportService.save(airport);
            redirectAttributes.addFlashAttribute("successMessage", "Airport saved successfully");
            return "redirect:/airports";
        } catch (Exception e) {
            bindingResult.rejectValue("code", "error.airport", "Airport code must be unique");
            model.addAttribute("org.springframework.validation.BindingResult.airport", bindingResult);
            return "airport/form";
        }
    }

    @GetMapping("/edit/{id}")
    public String editAirportForm(@PathVariable Long id, Model model) {
        model.addAttribute("airport", airportService.findById(id));
        return "airport/form";
    }

    @GetMapping("/delete/{id}")
    public String deleteAirport(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        airportService.deleteById(id);
        redirectAttributes.addFlashAttribute("successMessage", "Airport deleted successfully");
        return "redirect:/airports";
    }
}