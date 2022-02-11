/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tk.superherosightingsdatalayer.service;

import com.tk.superherosightingsdatalayer.dao.LocationDao;
import com.tk.superherosightingsdatalayer.dao.OrganizationDao;
import com.tk.superherosightingsdatalayer.dao.SightingDao;
import com.tk.superherosightingsdatalayer.dao.SuperheroDao;
import com.tk.superherosightingsdatalayer.dao.SuperpowerDao;
import com.tk.superherosightingsdatalayer.entities.Location;
import com.tk.superherosightingsdatalayer.entities.Sighting;
import com.tk.superherosightingsdatalayer.entities.Superhero;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author Tanner Kendall <tannerkendall@gmail.com>
 */
@Service
public class LocationServiceLayer {
    
    @Autowired
    SuperheroDao superheroDao;
    
    @Autowired
    LocationDao locationDao;
    
    @Autowired
    SightingDao sightingDao;
    
    public Location createLocation(String name, double latitude, double longitude, String description, String addressInfo){
        
        Location location = new Location();
        
        location.setName(name);
        location.setLatitude(latitude);
        location.setLongitude(longitude);
        location.setDescription(description);
        location.setAddressInfo(addressInfo);
        return location;
        
    }
    
    public boolean latitudeValidation(String latitude){
        
        try{
            double latValue = Double.parseDouble(latitude);
            if(latValue < -90 || latValue > 90){
                return false;
            }
            return true;
        }catch(Exception e){
            return false;
        }
        
    }
    
    public boolean longitudeValidation(String latitude){
        
        try{
            double lonValue = Double.parseDouble(latitude);
            if(lonValue < -180 || lonValue > 180){
                return false;
            }
            return true;
        }catch(Exception e){
            return false;
        }
        
    }
    
    public List<Sighting> getSightingsForLocation(Location location){
        return sightingDao.getSightingsForLocation(location);
    }
    
    public List<Superhero> getSuperheroesForLocation(Location location){
        return superheroDao.getSuperHeroesForLocation(location);
    }
    
    public Location getLocationById(int id){
        return locationDao.getLocationById(id);
    }
    
    public List<Location> getAllLocations(){
        return locationDao.getAllLocations();
    }
    
    public Location addLocation(Location location){
        return locationDao.addLocation(location);
    }
    
    public void editLocation(Location location){
        locationDao.updateLocation(location);
    }
    
    public void deleteLocationById(int id){
        locationDao.deleteLocationById(id);
    }
    
}
