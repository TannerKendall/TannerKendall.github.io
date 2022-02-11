/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tk.superherosightingsdatalayer.controllers;

import com.tk.superherosightingsdatalayer.entities.Organization;
import com.tk.superherosightingsdatalayer.entities.Superhero;
import com.tk.superherosightingsdatalayer.service.OrganizationServiceLayer;
import com.tk.superherosightingsdatalayer.service.SuperheroServiceLayer;
import java.util.ArrayList;
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
public class OrganizationController {
    
    @Autowired
    OrganizationServiceLayer orgService;
    
    @Autowired
    SuperheroServiceLayer superheroService;
    
    Set<ConstraintViolation<Organization>> violations = new HashSet<>();
    
    @GetMapping("organizations")
    public String displayOrganizations(Model model){
        
        List<Organization> organizations = orgService.getAllOrganizations();
        model.addAttribute("organizations", organizations);
        return "organizations";
        
    }
    
    @GetMapping("/organization/addOrganization")
    public String displayAddOrganization(Model model){
        
        List<Superhero> superheroes = superheroService.getAllSuperheroes();
        model.addAttribute("superheroes", superheroes);
        
        model.addAttribute("organization", new Organization());
        return "/organization/addOrganization";
        
    }
    
    @PostMapping("/organization/addOrganization")
    public String addOrganization(@Valid Organization org, BindingResult result, HttpServletRequest request, Model model){
        
        if(result.hasErrors()){
            List<Organization> organizations = orgService.getAllOrganizations();
            model.addAttribute("organizations", organizations);
            List<Superhero> superheroes = superheroService.getAllSuperheroes();
            model.addAttribute("superheroes", superheroes);
            return "organization/addOrganization";
        }
        
        String name = request.getParameter("name");
        boolean isHero = Boolean.parseBoolean(request.getParameter("isHero"));
        String description = request.getParameter("description");
        String address = request.getParameter("address");
        String contact = request.getParameter("contact");
        String[] superheroIds = request.getParameterValues("superheroId");
        
        List<Superhero> superheroes = new ArrayList<>();
        if(superheroIds != null){
            for(String superheroId : superheroIds){
                superheroes.add(superheroService.getSuperheroById(Integer.parseInt(superheroId)));
            }
        }
        
        Organization organization = orgService.createOrganization(name, isHero, description, address, contact, superheroes);
        
        orgService.addOrganization(organization);
        
        return "redirect:/organizations";
        
    }
    
    @GetMapping("/organization/deleteOrganization")
    public String deleteOrganization(Integer id){
        orgService.deleteOrganizationById(id);
        return "redirect:/organizations";
    }
    
    @GetMapping("/organization/editOrganization")
    public String displayEditOrganization(HttpServletRequest request, Model model){
        int id = Integer.parseInt(request.getParameter("id"));
        
        Organization organization = orgService.getOrganizationById(id);
        model.addAttribute("organization", organization);
        
        List<Superhero> superheroes = superheroService.getAllSuperheroes();
        model.addAttribute("superheroes", superheroes);
        
        //model.addAttribute("errors", violations);
        
        return "/organization/editOrganization";
    }
    
    @PostMapping("/organization/editOrganization")
    public String editOrganization(@Valid Organization org, BindingResult result, HttpServletRequest request, Model model){
        
        if(result.hasErrors()){
            List<Organization> organizations = orgService.getAllOrganizations();
            model.addAttribute("organizations", organizations);
            List<Superhero> superheroes = superheroService.getAllSuperheroes();
            model.addAttribute("superheroes", superheroes);
            return "organization/editOrganization";
        }
        
        int id = Integer.parseInt(request.getParameter("id"));
        String name = request.getParameter("name");
        boolean isHero = Boolean.parseBoolean(request.getParameter("isHero"));
        String description = request.getParameter("description");
        String address = request.getParameter("address");
        String contact = request.getParameter("contact");
        
        String[] superheroIds = request.getParameterValues("superheroId");
        List<Superhero> superheroes = new ArrayList<>();
        if(superheroIds != null){
            for(String superheroId : superheroIds){
                superheroes.add(superheroService.getSuperheroById(Integer.parseInt(superheroId)));
            }
        }
        
        Organization organization = orgService.createOrganization(name, isHero, description, address, contact, superheroes);
        organization.setId(id);
        
        orgService.editOrganization(organization);
        
        return "redirect:/organizations";
    }
    
    @GetMapping("organization/detailsOrganization")
    public String displayDetailsOrganization(HttpServletRequest request, Model model){
        int id = Integer.parseInt(request.getParameter("id"));
        
        Organization organization = orgService.getOrganizationById(id);
        model.addAttribute("organization", organization);
        
        return "organization/detailsOrganization";
    }
    
}
