/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tk.superherosightingsdatalayer.controllers;

import com.tk.superherosightingsdatalayer.entities.Location;
import com.tk.superherosightingsdatalayer.entities.Sighting;
import com.tk.superherosightingsdatalayer.entities.Superhero;
import com.tk.superherosightingsdatalayer.service.LocationServiceLayer;
import com.tk.superherosightingsdatalayer.service.SightingServiceLayer;
import com.tk.superherosightingsdatalayer.service.SuperheroServiceLayer;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

/**
 *
 * @author Tanner Kendall <tannerkendall@gmail.com>
 */
@Controller
public class SightingController {
    
    @Autowired
    SightingServiceLayer sightingService;
    
    @Autowired
    SuperheroServiceLayer superheroService;
    
    @Autowired
    LocationServiceLayer locationService;
    
    String dateError = null;
    
    @GetMapping("sightings")
    public String displaySightings(Model model){
        List<Sighting> sightings = sightingService.getAllSightings();
        HashMap<Sighting, Superhero> superheroSightings = sightingService.mapSuperheroSightings(sightings);
        model.addAttribute("sightings", sightings);
        model.addAttribute("superheroSightings", superheroSightings);
        return "sightings";
    }
    
    
    @GetMapping("/")
    public String displayRecentSightings(Model model){
        
        List<Sighting> sightings = sightingService.getRecentSightings();
        HashMap<Sighting, Superhero> superheroSightings = sightingService.mapSuperheroSightings(sightings);
        
        model.addAttribute("sightings", sightings);
        model.addAttribute("superheroSightings", superheroSightings);
        return "index";
        
    }
    
    @GetMapping("/sighting/addSighting")
    public String displayAddSighting(Model model){
        
        List<Superhero> superheroes = superheroService.getAllSuperheroes();
        model.addAttribute("superheroes", superheroes);
        
        List<Location> locations = locationService.getAllLocations();
        model.addAttribute("locations", locations);
        
        model.addAttribute("sighting", new Sighting());
        
        model.addAttribute("dateError", dateError);
        
        return "/sighting/addSighting";
        
    }
    
    @PostMapping("/sighting/addSighting")
    public String addSighting(@Valid Sighting sight, BindingResult result, HttpServletRequest request, Model model) {
        if(result.hasErrors()){
            List<Superhero> superheroes = superheroService.getAllSuperheroes();
            model.addAttribute("superheroes", superheroes);
            
            List<Location> locations = locationService.getAllLocations();
            model.addAttribute("locations", locations);
            
            return "/sighting/addSighting";
        }
        
        int superheroId = Integer.parseInt(request.getParameter("superheroId"));
        int locationId = Integer.parseInt(request.getParameter("locationId"));
        String dateString = request.getParameter("sightingDate");
        
        LocalDate date = LocalDate.parse(dateString);
        
        Superhero superhero = superheroService.getSuperheroById(superheroId);
        Location location = locationService.getLocationById(locationId);
        
        Sighting sighting = sightingService.createSighting(superhero, location, date);
        
        sightingService.addSighting(sighting);
        
        return "redirect:/sightings";
        
    }
    
    @GetMapping("sighting/deleteSighting")
    public String deleteSuperpower(Integer id){
        sightingService.deleteSightingById(id);
        return "redirect:/sightings";
    }
    
    @GetMapping("sighting/editSighting")
    public String getEditSighting(HttpServletRequest request, Model model){
        
        int id = Integer.parseInt(request.getParameter("id"));
        
        Sighting sighting = sightingService.getSightingById(id);
        model.addAttribute("sighting", sighting);
        
        List<Superhero> superheroes = superheroService.getAllSuperheroes();
        model.addAttribute("superheroes", superheroes);
        
        List<Location> locations = locationService.getAllLocations();
        model.addAttribute("locations", locations);
        
        model.addAttribute("dateError", dateError);
        
        return "sighting/editSighting";
        
    }
    
    @PostMapping("sighting/editSighting")
    public String editSighting(@Valid Sighting sight, BindingResult result, HttpServletRequest request, Model model){
        if(result.hasErrors()){
            List<Superhero> superheroes = superheroService.getAllSuperheroes();
            model.addAttribute("superheroes", superheroes);
            
            List<Location> locations = locationService.getAllLocations();
            model.addAttribute("locations", locations);
            
            return "/sighting/editSighting";
        }
        
        int id = Integer.parseInt(request.getParameter("id"));
        int superheroId = Integer.parseInt(request.getParameter("superheroId"));
        int locationId = Integer.parseInt(request.getParameter("locationId"));
        String dateString = request.getParameter("sightingDate");
        
        LocalDate date = LocalDate.parse(dateString);
        
        Superhero superhero = superheroService.getSuperheroById(superheroId);
        Location location = locationService.getLocationById(locationId);
        
        Sighting sighting = sightingService.createSighting(superhero, location, date);
        sighting.setId(id);
        
        sightingService.editSighting(sighting);
        return "redirect:/sightings";
        
    }
    
}
