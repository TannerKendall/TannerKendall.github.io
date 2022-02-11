/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tk.superherosightingsdatalayer.controllers;

import com.tk.superherosightingsdatalayer.entities.Location;
import com.tk.superherosightingsdatalayer.entities.Superhero;
import com.tk.superherosightingsdatalayer.service.LocationServiceLayer;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintViolation;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
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
public class LocationController {
    
    @Autowired
    LocationServiceLayer locationService;
    
    //Set<ConstraintViolation<Location>> violations = new HashSet<>();
    //String latError = null;
    //String lonError = null;
    
    @GetMapping("locations")
    public String displayLocations(Model model) {
        List<Location> locations = locationService.getAllLocations();
        model.addAttribute("locations", locations);
        return "locations";
    }
    
    @GetMapping("addLocation")
    public String displayAddLocations(Model model){
        model.addAttribute("location", new Location());
        return "/location/addLocation";
    }
    
    @PostMapping("addLocation")
    public String addLocation(@Valid Location loc, BindingResult result, HttpServletRequest request, Model model){
        
        if(result.hasErrors()){
            List<Location> locations = locationService.getAllLocations();
            model.addAttribute("locations", locations);
            return "location/addLocation";
        }
        
        String name = request.getParameter("name");
        String stringLat = request.getParameter("latitude");
        String stringLon = request.getParameter("longitude");
        String description = request.getParameter("description");
        String addressInfo = request.getParameter("addressInfo");
        
        Location location = locationService.createLocation(name, Double.parseDouble(stringLat), Double.parseDouble(stringLon), description, addressInfo);
        locationService.addLocation(location);
        
        return "redirect:/locations";
        
    }
    
    @GetMapping("location/deleteLocation")
    public String deleteLocation(Integer id){
        locationService.deleteLocationById(id);
        return "redirect:/locations";
    }
    
    @GetMapping("location/editLocation")
    public String getEditLocation(HttpServletRequest request, Model model){
        
        int id = Integer.parseInt(request.getParameter("id"));
        Location location = locationService.getLocationById(id);
        
        model.addAttribute("location", location);
        
        return "location/editLocation";
        
    }
    
    @PostMapping("location/editLocation")
    public String editLocation(@Valid Location loc, BindingResult result, HttpServletRequest request, Model model){
        
        if(result.hasErrors()){
            List<Location> locations = locationService.getAllLocations();
            model.addAttribute("locations", locations);
            return "location/editLocation";
        }
        /**
        int id = Integer.parseInt(request.getParameter("id"));
        String name = request.getParameter("name");
        String stringLat = request.getParameter("latitude");
        String stringLon = request.getParameter("longitude");
        String description = request.getParameter("description");
        String addressInfo = request.getParameter("addressInfo");
        **/
        locationService.editLocation(loc);
        
        return "redirect:/locations";
    }
    
    @GetMapping("location/detailsLocation")
    public String displayDetailsLocation(HttpServletRequest request, Model model){
        
        int id = Integer.parseInt(request.getParameter("id"));
        
        Location location = locationService.getLocationById(id);
        model.addAttribute("location", location);
        
        List<Superhero> superheroes = locationService.getSuperheroesForLocation(location);
        model.addAttribute("superheroes", superheroes);
        
        return "location/detailsLocation";
        
    }
    
}
