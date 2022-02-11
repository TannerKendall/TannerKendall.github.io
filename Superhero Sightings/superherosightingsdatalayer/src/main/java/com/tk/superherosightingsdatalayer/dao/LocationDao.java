/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tk.superherosightingsdatalayer.dao;

import com.tk.superherosightingsdatalayer.entities.Location;
import com.tk.superherosightingsdatalayer.entities.Superhero;
import java.util.List;

/**
 *
 * @author Tanner Kendall
 */
public interface LocationDao {
    
    Location getLocationById(int locationId);
    
    List<Location> getAllLocations();
    
    Location addLocation(Location location);
    
    void updateLocation(Location location);
    
    void deleteLocationById(int locationId);
    
    List<Location> getLocationsForSuperhero(Superhero superhero);
    
}
