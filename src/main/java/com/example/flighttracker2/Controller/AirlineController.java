package com.example.flighttracker2.Controller;

import com.example.flighttracker2.Model.Airline;
import com.example.flighttracker2.Service.AirlineService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/airlines")
@RequiredArgsConstructor
public class AirlineController {
    private final AirlineService airlineService;

    @GetMapping
    public String listAirlines(Model model) {
        model.addAttribute("airlines", airlineService.findAll());
        return "airline/list";
    }

    @GetMapping("/create")
    public String createAirlineForm(Model model) {
        model.addAttribute("airline", new Airline());
        return "airline/form";
    }

    @PostMapping
    public String saveAirline(@Valid @ModelAttribute Airline airline,
                              BindingResult bindingResult,
                              RedirectAttributes redirectAttributes,
                              Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("org.springframework.validation.BindingResult.airline", bindingResult);
            return "airline/form";
        }

        try {
            airlineService.save(airline);
            redirectAttributes.addFlashAttribute("successMessage", "Airline saved successfully");
            return "redirect:/airlines";
        } catch (Exception e) {
            bindingResult.rejectValue("code", "error.airline", "Airline code must be unique");
            model.addAttribute("org.springframework.validation.BindingResult.airline", bindingResult);
            return "airline/form";
        }
    }

    @GetMapping("/edit/{id}")
    public String editAirlineForm(@PathVariable Long id, Model model) {
        model.addAttribute("airline", airlineService.findById(id));
        return "airline/form";
    }

    @GetMapping("/delete/{id}")
    public String deleteAirline(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        airlineService.deleteById(id);
        redirectAttributes.addFlashAttribute("successMessage", "Airline deleted successfully");
        return "redirect:/airlines";
    }
}