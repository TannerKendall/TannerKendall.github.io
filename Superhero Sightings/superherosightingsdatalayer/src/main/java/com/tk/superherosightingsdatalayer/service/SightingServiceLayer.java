/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tk.superherosightingsdatalayer.service;

import com.tk.superherosightingsdatalayer.dao.SightingDao;
import com.tk.superherosightingsdatalayer.dao.SuperheroDao;
import com.tk.superherosightingsdatalayer.entities.Location;
import com.tk.superherosightingsdatalayer.entities.Sighting;
import com.tk.superherosightingsdatalayer.entities.Superhero;
import java.time.LocalDate;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author Tanner Kendall <tannerkendall@gmail.com>
 */
@Service
public class SightingServiceLayer {
    
    @Autowired
    SightingDao sightingDao;
    
    @Autowired
    SuperheroDao superheroDao;
    
    public Sighting createSighting(Superhero superhero, Location location, LocalDate date){
        Sighting sighting = new Sighting();
        sighting.setSuperheroId(superhero.getId());
        sighting.setLocation(location);
        sighting.setSightingDate(date);
        return sighting;
    }
    
    public boolean sightingDateValidation(String date){
        try{
            LocalDate.parse(date);
            return true;
        }catch(IllegalArgumentException ex){
            return false;
        }
    }
    
    public HashMap<Sighting, Superhero> mapSuperheroSightings(List<Sighting> sightings){
        
        HashMap<Sighting, Superhero> superheroSightings = new HashMap<>();
        for(int i = 0; i < sightings.size(); i++){
            superheroSightings.put(sightings.get(i), getSuperheroForSighting(sightings.get(i)));
        }
        return superheroSightings;
    }
    
    public static final class SortByDateDesc implements Comparator<Sighting>{

        @Override
        public int compare(Sighting s1, Sighting s2) {
            return s2.getSightingDate().compareTo(s1.getSightingDate());
        }
        
    }
    
    public Superhero getSuperheroForSighting(Sighting sighting){
        return superheroDao.getSuperheroForSighting(sighting);
    }
    
    public Sighting getSightingById(int id){
        return sightingDao.getSightingById(id);
    }
    
    public List<Sighting> getAllSightings(){
        return sightingDao.getAllSightings();
    }
    
    public Sighting addSighting(Sighting sighting){
        return sightingDao.addSighting(sighting);
    }
    
    public void editSighting(Sighting sighting){
        sightingDao.updateSighting(sighting);
    }
    
    public void deleteSightingById(int id){
        sightingDao.deleteSightingById(id);
    }
    
    public List<Sighting> getRecentSightings(){
        return sightingDao.getMostRecentSightings();
    }
    
}
