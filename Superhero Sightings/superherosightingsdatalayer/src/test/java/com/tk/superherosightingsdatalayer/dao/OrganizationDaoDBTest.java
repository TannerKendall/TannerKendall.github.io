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
public class OrganizationDaoDBTest {
    
    public OrganizationDaoDBTest() {
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
    
    @Test
    public void testAddGetOrganization() {
        
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
        
        List<Superhero> superheroes = new ArrayList<>();
        superheroes.add(superhero);
        
        Organization org = new Organization();
        org.setName("Test N");
        org.setIsHero(true);
        org.setDescription("Test D");
        org.setAddress("Test A");
        org.setContact("Test C");
        org.setOrgMembers(superheroes);
        org = organizationDao.addOrganization(org);
        
        // test getOrganizationById
        Organization fromDao = organizationDao.getOrganizationById(org.getId());
        
        assertEquals(org, fromDao);
        
    }
    
    @Test
    public void testGetAllOrganization(){
        
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
        
        List<Superhero> superheroes = new ArrayList<>();
        superheroes.add(superhero);
        
        Organization org = new Organization();
        org.setName("Test N");
        org.setIsHero(true);
        org.setDescription("Test D");
        org.setAddress("Test A");
        org.setContact("Test C");
        org.setOrgMembers(superheroes);
        org = organizationDao.addOrganization(org);
        
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
        
        Location location2 = new Location();
        location2.setName("Test N2");
        location2.setLatitude(10);
        location2.setLongitude(2.5);
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
        
        List<Superhero> superheroes2 = new ArrayList<>();
        superheroes2.add(superhero2);
        
        Organization org2 = new Organization();
        org2.setName("Test N2");
        org2.setIsHero(false);
        org2.setDescription("Test D2");
        org2.setAddress("Test A2");
        org2.setContact("Test C2");
        org2.setOrgMembers(superheroes2);
        org2 = organizationDao.addOrganization(org2);
        
        List<Organization> orgs = organizationDao.getAllOrganizations();
        
        //test getAllOrganizations
        assertTrue(orgs.contains(org));
        assertTrue(orgs.contains(org2));
        assertEquals(2, orgs.size());
        
    }
    
    @Test
    public void testUpdateDeleteOrganization(){
        
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
        
        List<Superhero> superheroes = new ArrayList<>();
        superheroes.add(superhero);
        
        Organization org = new Organization();
        org.setName("Test N");
        org.setIsHero(true);
        org.setDescription("Test D");
        org.setAddress("Test A");
        org.setContact("Test C");
        org.setOrgMembers(superheroes);
        org = organizationDao.addOrganization(org);
        
        Organization fromDao = organizationDao.getOrganizationById(org.getId());
        
        assertEquals(org, fromDao);
        
        org.setName("Test N2");
        org.setIsHero(false);
        org.setDescription("Test D2");
        org.setAddress("Test A2");
        org.setContact("Test C2");
        
        organizationDao.updateOrganization(org);
        
        assertNotEquals(org, fromDao);
        
        fromDao = organizationDao.getOrganizationById(org.getId());
        // test update, fromDao should be the same as org after update
        assertEquals(org, fromDao);
        
        organizationDao.deleteOrganizationById(org.getId());
        
        fromDao = organizationDao.getOrganizationById(org.getId());
        // test delete, fromDao should be null after deleting original org
        assertNull(fromDao);
        
    }
    
    @Test
    public void testGetOrganizationsForHero(){
        
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
        
        List<Superhero> superheroes = new ArrayList<>();
        superheroes.add(superhero);
        
        Organization org = new Organization();
        org.setName("Test N");
        org.setIsHero(true);
        org.setDescription("Test D");
        org.setAddress("Test A");
        org.setContact("Test C");
        org.setOrgMembers(superheroes);
        org = organizationDao.addOrganization(org);
        
        
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
        
        Location location2 = new Location();
        location2.setName("Test N2");
        location2.setLatitude(10.0);
        location2.setLongitude(2.5);
        location2.setDescription("Test D2");
        location2.setAddressInfo("Test I2");
        location2 = locationDao.addLocation(location2);
        
        Date testDate2 = Date.valueOf("2021-01-01");
        
        Sighting sighting2 = new Sighting();
        sighting2.setSuperheroId(superhero2.getId());
        sighting2.setLocation(location);
        sighting2.setSightingDate(testDate.toLocalDate());
        sighting2 = sightingDao.addSighting(sighting2);
        
        sightings2.add(sighting2);
        
        superhero2.setSightings(sightings2);
        
        List<Superhero> superheroes2 = new ArrayList<>();
        superheroes2.add(superhero2);
        
        Organization org2 = new Organization();
        org2.setName("Test N2");
        org2.setIsHero(false);
        org2.setDescription("Test D2");
        org2.setAddress("Test A2");
        org2.setContact("Test C2");
        org2.setOrgMembers(superheroes2);
        org2 = organizationDao.addOrganization(org2);
        
        List<Organization> fromDao = organizationDao.getOrganizationsForSuperhero(superhero);
        // test getOrganizationsForSuperhero, superhero should have org1, not org2
        assertTrue(fromDao.contains(org));
        assertFalse(fromDao.contains(org2));
    }
    
}
