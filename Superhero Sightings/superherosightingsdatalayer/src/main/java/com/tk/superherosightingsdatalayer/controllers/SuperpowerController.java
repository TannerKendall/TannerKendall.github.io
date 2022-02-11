/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tk.superherosightingsdatalayer.controllers;

import com.tk.superherosightingsdatalayer.entities.Superhero;
import com.tk.superherosightingsdatalayer.entities.Superpower;
import com.tk.superherosightingsdatalayer.service.SuperpowerServiceLayer;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
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
public class SuperpowerController {
    
    @Autowired
    SuperpowerServiceLayer superpowerService;
    
    @GetMapping("superpowers")
    public String displaySuperpowers(Model model){
        List<Superpower> superpowers = superpowerService.getAllSuperpowers();
        model.addAttribute("superpowers", superpowers);
        return "superpowers";
    }
    
    @GetMapping("addSuperpower")
    public String displayAddSuperpower(Model model){
        model.addAttribute("superpower", new Superpower());
        return "superpower/addSuperpower";
    }
    
    @PostMapping("addSuperpower")
    public String addSuperpower(@Valid Superpower sp, BindingResult result, HttpServletRequest request, Model model){
        if(result.hasErrors()){
            List<Superpower> superpowers = superpowerService.getAllSuperpowers();
            model.addAttribute("superpowers", superpowers);
            return "superpower/addSuperpower";
        }
        
        String name = request.getParameter("name");
        String description = request.getParameter("description");
        
        Superpower superpower = superpowerService.createSuperpower(name, description);
        superpowerService.addSuperpower(superpower);
        
        return "redirect:/superpowers";
    } 
    
    @GetMapping("superpower/deleteSuperpower")
    public String deleteSuperpower(Integer id){
        superpowerService.deleteSuperpowerById(id);
        return "redirect:/superpowers";
    }
    
    @GetMapping("superpower/editSuperpower")
    public String getEditSuperpower(HttpServletRequest request, Model model){
        
        int id = Integer.parseInt(request.getParameter("id"));
        Superpower superpower = superpowerService.getSuperpowerById(id);
        
        model.addAttribute("superpower", superpower);
        
        return "superpower/editSuperpower";
        
    }
    
    @PostMapping("superpower/editSuperpower")
    public String editSuperpower(@Valid Superpower sp, BindingResult result, HttpServletRequest request, Model model){
        
        if(result.hasErrors()){
            return "superpower/editSuperpower";
        }
            
        int id = Integer.parseInt(request.getParameter("id"));
        String name = request.getParameter("name");
        String description = request.getParameter("description");
        
        Superpower superpower = superpowerService.createSuperpower(name, description);
        superpower.setId(id);
        
        superpowerService.updateSuperpower(superpower);
        return "redirect:/superpowers";
        
        
    }
    
    @GetMapping("superpower/detailsSuperpower")
    public String displayDetailsSuperpower(HttpServletRequest request, Model model){
        
        int id = Integer.parseInt(request.getParameter("id"));
        
        Superpower superpower = superpowerService.getSuperpowerById(id);
        model.addAttribute("superpower", superpower);
        
        List<Superhero> superheroes = superpowerService.getSuperheroesForSuperpower(superpower);
        model.addAttribute("superheroes", superheroes);
        
        return "superpower/detailsSuperpower";
    }
    
}
