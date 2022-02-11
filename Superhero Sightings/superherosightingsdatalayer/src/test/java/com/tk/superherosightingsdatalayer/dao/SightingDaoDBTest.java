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
import java.sql.Date;
import java.util.ArrayList;
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
public class SightingDaoDBTest {
    
    public SightingDaoDBTest() {
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
    public void testAddGetSighting() {
        
        Superpower superpower = new Superpower();
        superpower.setName("Test N");
        superpower.setDescription("Test D");
        superpower = superpowerDao.addSuperpower(superpower);
        
        List<Superpower> superpowers = new ArrayList<>();
        superpowers.add(superpower);
        
        List<Sighting> sightings = new ArrayList<>();
        
        Superhero superhero = new Superhero();
        superhero.setIsHero(true);
        superhero.setName("Test N");
        superhero.setDescription("Test D");
        superhero.setSuperpowers(superpowers);
        superhero.setSightings(sightings);
        superhero = superheroDao.addSuperhero(superhero);
        
        Location location = new Location();
        location.setName("Test N");
        location.setLatitude(9.7);
        location.setLongitude(-2.5);
        location.setDescription("Test D");
        location.setAddressInfo("Test I");
        location = locationDao.addLocation(location);
        
        Date testDate = Date.valueOf("2021-01-01");
        
        Sighting sighting = new Sighting();
        sighting.setSuperheroId(superhero.getId());
        sighting.setLocation(location);
        sighting.setSightingDate(testDate.toLocalDate());
        sighting = sightingDao.addSighting(sighting);
        
        Sighting fromDao = sightingDao.getSightingById(sighting.getId());
        //test add/get sighting
        assertEquals(sighting, fromDao);
        
    }
    
    @Test
    public void testGetAllSightings(){
        
        Superpower superpower = new Superpower();
        superpower.setName("Test N");
        superpower.setDescription("Test D");
        superpower = superpowerDao.addSuperpower(superpower);
        
        List<Superpower> superpowers = new ArrayList<>();
        superpowers.add(superpower);
        
        List<Sighting> sightings = new ArrayList<>();
        
        Superhero superhero = new Superhero();
        superhero.setIsHero(true);
        superhero.setName("Test N");
        superhero.setDescription("Test D");
        superhero.setSuperpowers(superpowers);
        superhero.setSightings(sightings);
        superhero = superheroDao.addSuperhero(superhero);
        
        Location location = new Location();
        location.setName("Test N");
        location.setLatitude(9.7);
        location.setLongitude(-2.5);
        location.setDescription("Test D");
        location.setAddressInfo("Test I");
        location = locationDao.addLocation(location);
        
        Date testDate = Date.valueOf("2021-01-01");
        
        Sighting sighting = new Sighting();
        sighting.setSuperheroId(superhero.getId());
        sighting.setLocation(location);
        sighting.setSightingDate(testDate.toLocalDate());
        sighting = sightingDao.addSighting(sighting);
        
        Superpower superpower2 = new Superpower();
        superpower2.setName("Test N2");
        superpower2.setDescription("Test D2");
        superpower2 = superpowerDao.addSuperpower(superpower2);
        
        List<Superpower> superpowers2 = new ArrayList<>();
        superpowers2.add(superpower2);
        
        List<Sighting> sightings2 = new ArrayList<>();
        
        Superhero superhero2 = new Superhero();
        superhero2.setIsHero(true);
        superhero2.setName("Test N2");
        superhero2.setDescription("Test D2");
        superhero2.setSuperpowers(superpowers2);
        superhero2.setSightings(sightings2);
        superhero2 = superheroDao.addSuperhero(superhero2);
        
        Location location2 = new Location();
        location2.setName("Test N2");
        location2.setLatitude(9.7);
        location2.setLongitude(-2.5);
        location2.setDescription("Test D2");
        location2.setAddressInfo("Test I2");
        location2 = locationDao.addLocation(location2);
        
        Date testDate2 = Date.valueOf("2021-01-02");
        
        Sighting sighting2 = new Sighting();
        sighting2.setSuperheroId(superhero2.getId());
        sighting2.setLocation(location2);
        sighting2.setSightingDate(testDate2.toLocalDate());
        sighting2 = sightingDao.addSighting(sighting2);
        
        List<Sighting> allSightings = sightingDao.getAllSightings();
        
        //test getAllSightings
        assertEquals(2, allSightings.size());
        assertTrue(allSightings.contains(sighting));
        assertTrue(allSightings.contains(sighting2));
        
    }
    
    @Test
    public void testUpdateDeleteSighting(){
        
        Superpower superpower = new Superpower();
        superpower.setName("Test N");
        superpower.setDescription("Test D");
        superpower = superpowerDao.addSuperpower(superpower);
        
        List<Superpower> superpowers = new ArrayList<>();
        superpowers.add(superpower);
        
        List<Sighting> sightings = new ArrayList<>();
        
        Superhero superhero = new Superhero();
        superhero.setIsHero(true);
        superhero.setName("Test N");
        superhero.setDescription("Test D");
        superhero.setSuperpowers(superpowers);
        superhero.setSightings(sightings);
        superhero = superheroDao.addSuperhero(superhero);
        
        Location location = new Location();
        location.setName("Test N");
        location.setLatitude(9.7);
        location.setLongitude(-2.5);
        location.setDescription("Test D");
        location.setAddressInfo("Test I");
        location = locationDao.addLocation(location);
        
        Date testDate = Date.valueOf("2021-01-01");
        
        Sighting sighting = new Sighting();
        sighting.setSuperheroId(superhero.getId());
        sighting.setLocation(location);
        sighting.setSightingDate(testDate.toLocalDate());
        sighting = sightingDao.addSighting(sighting);
        sightings.add(sighting);
        superhero.setSightings(sightings);
        
        Sighting fromDao = sightingDao.getSightingById(sighting.getId());
        assertEquals(sighting, fromDao);
        
        Date testDate2 = Date.valueOf("2021-01-02");
        sighting.setSightingDate(testDate2.toLocalDate());
        
        sightingDao.updateSighting(sighting);
        // test update, fromDao should equal sighting after update
        assertNotEquals(sighting, fromDao);
        
        sightingDao.deleteSightingById(sighting.getId());
        fromDao = sightingDao.getSightingById(sighting.getId());
        // test delete, fromDao should be null after deleting sighting
        assertNull(fromDao);
        
    }
    
    @Test
    public void testGetSightingsForLocation(){
        
        Superpower superpower = new Superpower();
        superpower.setName("Test N");
        superpower.setDescription("Test D");
        superpower = superpowerDao.addSuperpower(superpower);
        
        List<Superpower> superpowers = new ArrayList<>();
        superpowers.add(superpower);
        
        List<Sighting> sightings = new ArrayList<>();
        
        Superhero superhero = new Superhero();
        superhero.setIsHero(true);
        superhero.setName("Test N");
        superhero.setDescription("Test D");
        superhero.setSuperpowers(superpowers);
        superhero.setSightings(sightings);
        superhero = superheroDao.addSuperhero(superhero);
        
        Location location = new Location();
        location.setName("Test N");
        location.setLatitude(9.7);
        location.setLongitude(-2.5);
        location.setDescription("Test D");
        location.setAddressInfo("Test I");
        location = locationDao.addLocation(location);
        
        Date testDate = Date.valueOf("2021-01-01");
        
        Sighting sighting = new Sighting();
        sighting.setSuperheroId(superhero.getId());
        sighting.setLocation(location);
        sighting.setSightingDate(testDate.toLocalDate());
        sighting = sightingDao.addSighting(sighting);
        sightings.add(sighting);
        superhero.setSightings(sightings);
        
        Superpower superpower2 = new Superpower();
        superpower2.setName("Test N2");
        superpower2.setDescription("Test D2");
        superpower2 = superpowerDao.addSuperpower(superpower2);
        
        List<Superpower> superpowers2 = new ArrayList<>();
        superpowers2.add(superpower2);
        
        List<Sighting> sightings2 = new ArrayList<>();
        
        Superhero superhero2 = new Superhero();
        superhero2.setIsHero(true);
        superhero2.setName("Test N2");
        superhero2.setDescription("Test D2");
        superhero2.setSuperpowers(superpowers2);
        superhero2.setSightings(sightings2);
        superhero2 = superheroDao.addSuperhero(superhero2);
        
        Location location2 = new Location();
        location2.setName("Test N2");
        location2.setLatitude(9.7);
        location2.setLongitude(-2.5);
        location2.setDescription("Test D2");
        location2.setAddressInfo("Test I2");
        location2 = locationDao.addLocation(location2);
        
        Date testDate2 = Date.valueOf("2021-01-02");
        
        Sighting sighting2 = new Sighting();
        sighting2.setSuperheroId(superhero2.getId());
        sighting2.setLocation(location2);
        sighting2.setSightingDate(testDate2.toLocalDate());
        sighting2 = sightingDao.addSighting(sighting2);
        
        List<Sighting> testSightings = sightingDao.getSightingsForLocation(location);
        
        //// test getSightingsForLocation, should only include sighting1 with use of location1
        assertTrue(testSightings.contains(sighting));
        assertFalse(testSightings.contains(sighting2));
        
    }
    
}
