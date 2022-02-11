/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tk.superherosightingsdatalayer.dao;

import com.tk.superherosightingsdatalayer.entities.Location;
import com.tk.superherosightingsdatalayer.entities.Sighting;
import com.tk.superherosightingsdatalayer.entities.Superhero;
import java.util.List;

/**
 *
 * @author Tanner Kendall
 */
public interface SightingDao {
    
    Sighting getSightingById(int sightingId);
    
    List<Sighting> getAllSightings();
    
    void associateLocationsToSightings(List<Sighting> sightings);
    
    Sighting addSighting(Sighting sighting);
    
    void updateSighting(Sighting sighting);
    
    void deleteSightingById(int sightingId);
    
    List<Sighting> getSightingsForLocation(Location location);
    
    List<Sighting> getSightingsForSuperhero(Superhero superhero);
    
    List<Sighting> getMostRecentSightings();
    
}
