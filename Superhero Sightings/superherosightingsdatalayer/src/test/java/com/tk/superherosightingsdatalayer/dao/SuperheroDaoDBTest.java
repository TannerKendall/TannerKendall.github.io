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
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
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
public class SuperheroDaoDBTest {
    
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
    
    public SuperheroDaoDBTest() {
    }
    
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
    public void testGetAddSuperhero() {
        
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
        
        // add/get test
        Superhero getTest = superheroDao.getSuperheroById(superhero.getId());
        
        assertEquals(superhero, getTest);
        
    }
    
    @Test public void testGetAllSuperheroes(){
    
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
        //add superhero 1
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
        superhero2.setIsHero(false);
        superhero2.setName("Test N2");
        superhero2.setDescription("Test D2");
        superhero2.setSuperpowers(superpowers2);
        superhero2.setSightings(sightings2);
        // add superhero 2
        superhero2 = superheroDao.addSuperhero(superhero2);
        
        Location location2 = new Location();
        location2.setName("Test N2");
        location2.setLatitude(1.5);
        location2.setLongitude(6.4);
        location2.setDescription("Test D2");
        location2.setAddressInfo("Test I2");
        location2 = locationDao.addLocation(location2);
        
        Date testDate2 = Date.valueOf("2021-01-02");
        
        Sighting sighting2 = new Sighting();
        sighting2.setSuperheroId(superhero2.getId());
        sighting2.setLocation(location2);
        sighting2.setSightingDate(testDate2.toLocalDate());
        sighting2 = sightingDao.addSighting(sighting2);
        
        sightings2.add(sighting2);
        
        superhero2.setSightings(sightings2);
        
        // getAll test
        List<Superhero> superheroes = superheroDao.getAllSuperheroes();
        assertEquals(2, superheroes.size());
        assertTrue(superheroes.contains(superhero));
        assertTrue(superheroes.contains(superhero2));
    }
    
    @Test
    public void testUpdateSuperhero(){
        
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
        
        Superhero fromDao = superheroDao.getSuperheroById(superhero.getId());
        // test checking superhero to fromDao are equal
        assertEquals(superhero, fromDao);
        
        Date testDate2 = Date.valueOf("2021-01-02");
        
        Sighting sighting2 = new Sighting();
        sighting2.setSuperheroId(superhero.getId());
        sighting2.setLocation(location);
        sighting2.setSightingDate(testDate2.toLocalDate());
        sighting2 = sightingDao.addSighting(sighting2);
        
        sightings.add(sighting2);
        
        superhero.setSightings(sightings);
        // updating superhero
        superheroDao.updateSuperhero(superhero);
        
        // test that superhero and fromDao are not equal after updating superhero
        assertNotEquals(superhero, fromDao);
        
        fromDao = superheroDao.getSuperheroById(superhero.getId());
        // test to check update saved
        assertEquals(superhero, fromDao);
        
    }
    
    @Test
    public void testDeleteSuperheroById(){
        
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
        // add superhero
        sightings.add(sighting);
        
        superhero.setSightings(sightings);
        // get superhero
        Superhero fromDao = superheroDao.getSuperheroById(superhero.getId());
        // test that they are equal
        assertEquals(superhero, fromDao);
        //delete superhero
        superheroDao.deleteSuperheroById(superhero.getId());
        //check that superhero is removed from dao
        fromDao = superheroDao.getSuperheroById(superhero.getId());
        
        assertNull(fromDao);
        
    }
    
    @Test
    public void testGetSuperheroesForSuperpower(){
        
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
        
        Superpower superpower2 = new Superpower();
        superpower2.setName("Test N2");
        superpower2.setDescription("Test D2");
        superpower2 = superpowerDao.addSuperpower(superpower2);
        
        List<Superpower> superpowers2 = new ArrayList<>();
        superpowers2.add(superpower2);
        
        List<Sighting> sightings2 = new ArrayList<>();
         
        Superhero superhero2 = new Superhero();
        superhero2.setIsHero(false);
        superhero2.setName("Test N2");
        superhero2.setDescription("Test D2");
        superhero2.setSuperpowers(superpowers2);
        superhero2.setSightings(sightings2);
        superhero2 = superheroDao.addSuperhero(superhero2);
        
        List<Sighting> sightings3 = new ArrayList<>();
        
        // assign superpower 1 to superhero 3
        Superhero superhero3 = new Superhero();
        superhero3.setIsHero(false);
        superhero3.setName("Test N3");
        superhero3.setDescription("Test D3");
        superhero3.setSuperpowers(superpowers);
        superhero3.setSightings(sightings3);
        superhero3 = superheroDao.addSuperhero(superhero3);
        
        // test that list contains superhero 1 & 3
        List<Superhero> superheroes = superheroDao.getSuperheroesForSuperpower(superpower);
        assertTrue(superheroes.contains(superhero));
        assertFalse(superheroes.contains(superhero2));
        assertTrue(superheroes.contains(superhero3));
        
    }
    
    @Test
    public void getSuperheroesForSightings(){
        
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
        
        superheroDao.updateSuperhero(superhero);
        
        Superpower superpower2 = new Superpower();
        superpower2.setName("Test N2");
        superpower2.setDescription("Test D2");
        superpower2 = superpowerDao.addSuperpower(superpower2);
        
        List<Superpower> superpowers2 = new ArrayList<>();
        superpowers2.add(superpower);
        
        List<Sighting> sightings2 = new ArrayList<>();
        
        Superhero superhero2 = new Superhero();
        superhero2.setIsHero(false);
        superhero2.setName("Test N2");
        superhero2.setDescription("Test D2");
        superhero2.setSuperpowers(superpowers2);
        superhero2.setSightings(sightings2);
        superhero2 = superheroDao.addSuperhero(superhero2);
        
        Location location2 = new Location();
        location2.setName("Test N2");
        location2.setLatitude(5.5);
        location2.setLongitude(4.5);
        location2.setDescription("Test D2");
        location2.setAddressInfo("Test I2");
        location2 = locationDao.addLocation(location2);
        
        Date testDate2 = Date.valueOf("2021-01-02");
        
        Sighting sighting2 = new Sighting();
        sighting2.setSuperheroId(superhero2.getId());
        sighting2.setLocation(location2);
        sighting2.setSightingDate(testDate2.toLocalDate());
        sighting2 = sightingDao.addSighting(sighting2);
        
        sightings2.add(sighting2);
        
        superhero2.setSightings(sightings2);
        
        superheroDao.updateSuperhero(superhero2);
        
        // test getSuperheroForSighting, superhero 1 & fromDao should be equal
        Superhero fromDao = superheroDao.getSuperheroForSighting(sighting);
        
        assertEquals(superhero, fromDao);
        assertNotEquals(superhero2, fromDao);
        
    }
    
    
    @Test
    public void testGetSuperheroesForLocation(){
        
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
        superpowers2.add(superpower);
        
        List<Sighting> sightings2 = new ArrayList<>();
        
        Superhero superhero2 = new Superhero();
        superhero2.setIsHero(false);
        superhero2.setName("Test N2");
        superhero2.setDescription("Test D2");
        superhero2.setSuperpowers(superpowers2);
        superhero2.setSightings(sightings2);
        superhero2 = superheroDao.addSuperhero(superhero2);
        
        Location location2 = new Location();
        location2.setName("Test N2");
        location2.setLatitude(5.5);
        location2.setLongitude(4.5);
        location2.setDescription("Test D2");
        location2.setAddressInfo("Test I2");
        location2 = locationDao.addLocation(location2);
        
        Date testDate2 = Date.valueOf("2021-01-02");
        
        Sighting sighting2 = new Sighting();
        sighting2.setSuperheroId(superhero2.getId());
        sighting2.setLocation(location2);
        sighting2.setSightingDate(testDate2.toLocalDate());
        sighting2 = sightingDao.addSighting(sighting2);
        
        sightings2.add(sighting2);
        
        superhero2.setSightings(sightings2);
        
        superheroDao.updateSuperhero(superhero2);
        
        // test getSuperheroes for location, list should contain superhero1 with location1
        List<Superhero> superheroes = superheroDao.getSuperHeroesForLocation(location);
        
        assertTrue(superheroes.contains(superhero));
        assertFalse(superheroes.contains(superhero2));
        
    }
}
