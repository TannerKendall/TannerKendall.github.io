/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tk.superherosightingsdatalayer.dao;

import com.tk.superherosightingsdatalayer.entities.Location;
import com.tk.superherosightingsdatalayer.entities.Organization;
import com.tk.superherosightingsdatalayer.entities.Sighting;
import com.tk.superherosightingsdatalayer.entities.Superhero;
import com.tk.superherosightingsdatalayer.entities.Superpower;
import java.util.List;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

/**
 *
 * @author Tanner Kendall
 */
@ExtendWith(SpringExtension.class)
@SpringBootTest
public class LocationDaoDBTest {
    
    public LocationDaoDBTest() {
    }
    
    @Autowired
    SuperheroDao superheroDao;
    
    @Autowired
    SuperpowerDao superpowerDao;
    
    @Autowired
    OrganizationDao organizationDao;
    
    @Autowired
    LocationDao locationDao;
    
    @Autowired
    SightingDao sightingDao;
    
    @BeforeAll
    public static void setUpClass() {
    }
    
    @AfterAll
    public static void tearDownClass() {
    }
    
    @BeforeEach
    public void setUp() {
        
        List<Superhero> superheroes = superheroDao.getAllSuperheroes();
        for(Superhero superhero : superheroes){
            superheroDao.deleteSuperheroById(superhero.getId());
        }
        
        List<Superpower> superpowers = superpowerDao.getAllSuperpowers();
        for(Superpower superpower : superpowers){
            superpowerDao.deleteSuperpowerById(superpower.getId());
        }
        
        List<Organization> organizations = organizationDao.getAllOrganizations();
        for(Organization organization : organizations){
            organizationDao.deleteOrganizationById(organization.getId());
        }
        
        List<Location> locations = locationDao.getAllLocations();
        for(Location location : locations){
            locationDao.deleteLocationById(location.getId());
        }
        
        List<Sighting> sightings = sightingDao.getAllSightings();
        for(Sighting sighting : sightings){
            sightingDao.deleteSightingById(sighting.getId());
        }
        
    }
    
    @AfterEach
    public void tearDown() {
    }

    @Test
    public void testAddGetLocation() {
        
        Location location = new Location();
        location.setName("Test N");
        location.setLatitude(9.7);
        location.setLongitude(-2.5);
        location.setDescription("Test D");
        location.setAddressInfo("Test I");
        location = locationDao.addLocation(location);
        
        Location fromDao = locationDao.getLocationById(location.getId());
        // test add/get location
        assertEquals(location, fromDao);
        
    }
    
    @Test
    public void testGetAllLocations(){
        
        Location location = new Location();
        location.setName("Test N");
        location.setLatitude(9.7);
        location.setLongitude(-2.5);
        location.setDescription("Test D");
        location.setAddressInfo("Test I");
        location = locationDao.addLocation(location);
        
        Location location2 = new Location();
        location2.setName("Test N2");
        location2.setLatitude(5.5);
        location2.setLongitude(4.5);
        location2.setDescription("Test D2");
        location2.setAddressInfo("Test I2");
        location2 = locationDao.addLocation(location2);
        
        List<Location> locations = locationDao.getAllLocations();
        
        //test getAll
        assertTrue(locations.contains(location));
        assertTrue(locations.contains(location2));
        assertEquals(2, locations.size());
        
    }
    
    @Test
    public void testUpdateDeleteLocation(){
        
        Location location = new Location();
        location.setName("Test N");
        location.setLatitude(9.7);
        location.setLongitude(-2.5);
        location.setDescription("Test D");
        location.setAddressInfo("Test I");
        location = locationDao.addLocation(location);
        
        Location fromDao = locationDao.getLocationById(location.getId());
        
        assertEquals(location, fromDao);
        
        location.setName("Test N2");
        location.setLatitude(10.0);
        location.setLongitude(2.0);
        location.setDescription("Test D2");
        location.setAddressInfo("Test I2");
        
        locationDao.updateLocation(location);
        // test update, fromDao should not equal location after update
        assertNotEquals(location, fromDao);
        
        fromDao = locationDao.getLocationById(location.getId());
        
        assertEquals(location, fromDao);
        
        locationDao.deleteLocationById(location.getId());
        
        fromDao = locationDao.getLocationById(location.getId());
        // test delete, fromDao should be null after deleting location
        assertNull(fromDao);
        
    }
    
}
