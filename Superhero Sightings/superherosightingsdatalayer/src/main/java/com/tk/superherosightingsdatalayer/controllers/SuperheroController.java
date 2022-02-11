/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tk.superherosightingsdatalayer.controllers;

import com.tk.superherosightingsdatalayer.entities.Organization;
import com.tk.superherosightingsdatalayer.entities.Sighting;
import com.tk.superherosightingsdatalayer.entities.Superhero;
import com.tk.superherosightingsdatalayer.entities.Superpower;
import com.tk.superherosightingsdatalayer.service.SuperheroServiceLayer;
import com.tk.superherosightingsdatalayer.service.SuperpowerServiceLayer;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

/**
 *
 * @author Tanner Kendall <tannerkendall@gmail.com>
 */
@Controller
public class SuperheroController {
    
    SuperheroServiceLayer superheroService;
    SuperpowerServiceLayer superpowerService;

    public SuperheroController(SuperheroServiceLayer superheroService, SuperpowerServiceLayer superpowerService) {
        this.superheroService = superheroService;
        this.superpowerService = superpowerService;
    }
    
    //private boolean displayImage = false;
    
    @GetMapping("superheroes")
    public String displaySuperheroes(Model model) {
        List<Superhero> superheroes = superheroService.getAllSuperheroes();
        model.addAttribute("superheroes", superheroes);
        return "superheroes";
    }
    
    @GetMapping("addSuperhero")
    public String displayAddSuperheroes(Model model){
        List<Superpower> superpowers = superpowerService.getAllSuperpowers();
        model.addAttribute("superpowers", superpowers);
        model.addAttribute("superhero", new Superhero());
        return "/superhero/addSuperhero";
    }
    
    @PostMapping("addSuperhero")
    public String addSuperhero(@Valid Superhero hero, BindingResult result, HttpServletRequest request, Model model){
        if(result.hasErrors()){
            List<Superhero> superheroes = superheroService.getAllSuperheroes();
            List<Superpower> superpowers = superpowerService.getAllSuperpowers();
            model.addAttribute("superpowers", superpowers);
            model.addAttribute("superheroes", superheroes);
            return "superhero/addSuperhero";
        }
        
        String name = request.getParameter("name");
        String description = request.getParameter("description");
        Boolean isHero = Boolean.parseBoolean(request.getParameter("isSuperhero"));
        String[] superpowerIds = request.getParameterValues("superpowerId");
        
        ArrayList<Superpower> superpowers = new ArrayList<>();
        
        if(superpowerIds != null){
            for(String superpowerId : superpowerIds){
                superpowers.add(superpowerService.getSuperpowerById(Integer.parseInt(superpowerId)));
            }
        }
        
        Superhero superhero = superheroService.createSuperhero(name, description, isHero, superpowers);
        
        superheroService.addSuperhero(superhero);
        
        return "redirect:superheroes";
        
    }
    
    @GetMapping("superhero/deleteSuperhero")
    public String deleteSuperhero(HttpServletRequest request){
        
        int id = Integer.parseInt(request.getParameter("id"));
        superheroService.deleteSuperheroById(id);
        
        return "redirect:/superheroes";
        
    }
    
    @GetMapping("superhero/editSuperhero")
    public String getEditSuperhero(HttpServletRequest request, Model model){
        
        int id = Integer.parseInt(request.getParameter("id"));
        Superhero superhero = superheroService.getSuperheroById(id);
        
        model.addAttribute("superhero", superhero);
        
        List<Superpower> superpowers = superpowerService.getAllSuperpowers();
        model.addAttribute("superpowers", superpowers);
        
        return "superhero/editSuperhero";
        
    }
    
    @PostMapping("superhero/editSuperhero")
    public String editSuperhero(@Valid Superhero hero, BindingResult result, HttpServletRequest request, Model model, @RequestParam("image") MultipartFile multipartFile){
        if(result.hasErrors()){
            List<Superpower> superpowers = superpowerService.getAllSuperpowers();
            model.addAttribute("superpowers", superpowers);
            List<Superhero> superheroes = superheroService.getAllSuperheroes();
            model.addAttribute("superheroes", superheroes);
            return "superhero/editSuperhero";
        }
        
        int id = Integer.parseInt(request.getParameter("id"));
        String name = request.getParameter("name");
        boolean isHero = Boolean.parseBoolean(request.getParameter("isHero"));
        String description = request.getParameter("description");
        String[] superpowerIds = request.getParameterValues("superpowerId");
        
        ArrayList<Superpower> superpowers = new ArrayList<>();
        if(superpowerIds != null){
            for(String superpowerId : superpowerIds){
                superpowers.add(superpowerService.getSuperpowerById(Integer.parseInt(superpowerId)));
            }
        }
        
        List<Sighting> sightingTemp = superheroService.getSuperheroById(id).getSightings();
        Superhero superhero = superheroService.createSuperhero(name, description, isHero, superpowers);
        superhero.setSightings(sightingTemp);
        superhero.setId(id);
        
        superheroService.updateSuperhero(superhero);
        
        return "redirect:/superheroes";
        
    }
    
    @GetMapping("superhero/detailsSuperhero")
    public String displayDetailsSuperhero(HttpServletRequest request, Model model){
        
        int id = Integer.parseInt(request.getParameter("id"));
        
        Superhero superhero = superheroService.getSuperheroById(id);
        model.addAttribute("superhero", superhero);
        
        List<Organization> organizations = superheroService.getOrganizationsForSuperhero(superhero);
        model.addAttribute("organizations", organizations);
        
        List<Sighting> sightings = superheroService.getSightingsForSuperhero(superhero);
        model.addAttribute("sightings", sightings);
        
        //displayImage = superheroService.isImageSet(superhero.getId() + "");
        //model.addAttribute("displayImg", displayImage);
        
        return "superhero/detailsSuperhero";
        
    }
    
}
