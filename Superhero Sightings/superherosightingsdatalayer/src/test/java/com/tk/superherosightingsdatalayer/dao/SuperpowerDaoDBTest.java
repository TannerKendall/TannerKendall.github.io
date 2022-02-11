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
public class SuperpowerDaoDBTest {
    
    public SuperpowerDaoDBTest() {
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
    public void testAddGetSuperpower(){
        
        Superpower superpower = new Superpower();
        superpower.setName("Test N");
        superpower.setDescription("Test D");
        superpower = superpowerDao.addSuperpower(superpower);
        
        Superpower fromDao = superpowerDao.getSuperpowerById(superpower.getId());
        // test add/get superpower
        assertEquals(superpower, fromDao);
        
    }
    
    @Test
    public void testGetAllSuperpowers() {
        
        Superpower superpower = new Superpower();
        superpower.setName("Test N");
        superpower.setDescription("Test D");
        superpower = superpowerDao.addSuperpower(superpower);
        
        Superpower superpower2 = new Superpower();
        superpower2.setName("Test N2");
        superpower2.setDescription("Test D2");
        superpower2 = superpowerDao.addSuperpower(superpower2);
        
        List<Superpower> superpowers = superpowerDao.getAllSuperpowers();
        //test get all superpowers
        assertTrue(superpowers.contains(superpower));
        assertTrue(superpowers.contains(superpower2));
        assertEquals(2, superpowers.size());
        
    }
    
    @Test
    public void testUpdateDeleteSuperpower(){
        
        Superpower superpower = new Superpower();
        superpower.setName("Test N");
        superpower.setDescription("Test D");
        superpower = superpowerDao.addSuperpower(superpower);
        
        Superpower fromDao = superpowerDao.getSuperpowerById(superpower.getId());
        
        assertEquals(superpower, fromDao);
        
        superpower.setName("Test N2");
        superpower.setDescription("Test D2");
        
        superpowerDao.updateSuperpower(superpower);
        
        assertNotEquals(superpower, fromDao);
        
        fromDao = superpowerDao.getSuperpowerById(superpower.getId());
        // test update, fromDao should equal superpower after update
        assertEquals(superpower, fromDao);
        
        superpowerDao.deleteSuperpowerById(superpower.getId());
        
        fromDao = superpowerDao.getSuperpowerById(superpower.getId());
        // fromDao should be null after deleting superpower
        assertNull(fromDao);
        
    }
    
}
