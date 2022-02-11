/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tk.superherosightingsdatalayer.dao;

import com.tk.superherosightingsdatalayer.dao.SightingDaoDB.SightingMapper;
import com.tk.superherosightingsdatalayer.dao.SuperpowerDaoDB.SuperpowerMapper;
import com.tk.superherosightingsdatalayer.entities.Location;
import com.tk.superherosightingsdatalayer.entities.Sighting;
import com.tk.superherosightingsdatalayer.entities.Superhero;
import com.tk.superherosightingsdatalayer.entities.Superpower;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author Tanner Kendall
 */
@Repository
public class SuperheroDaoDB implements SuperheroDao {

    @Autowired
    JdbcTemplate jdbc;
    
    @Autowired
    SightingDaoDB sightingDao;
    
    @Override
    public Superhero getSuperheroById(int superheroId) {
        
        try{
            final String GET_SUPERHERO_BY_ID = "SELECT * FROM superhero WHERE id = ?";
            Superhero superhero = jdbc.queryForObject(GET_SUPERHERO_BY_ID, new SuperheroMapper(), superheroId);
            superhero.setSuperpowers(getSuperpowersForSuperhero(superhero.getId()));
            superhero.setSightings(getSightingsForSuperhero(superhero.getId()));
            return superhero;
        }catch(DataAccessException ex){
            return null;
        }
        
    }
    
    private List<Superpower> getSuperpowersForSuperhero(int superheroId){
        
        final String SELECT_SUPERPOWERS_FOR_SUPERHERO = "SELECT s.* FROM superpower s JOIN hero_superpower hs ON "
                + "hs.superpowerId = s.id WHERE hs.superheroId = ?";
        return jdbc.query(SELECT_SUPERPOWERS_FOR_SUPERHERO, new SuperpowerMapper(), superheroId);
        
    }

    @Override
    public List<Sighting> getSightingsForSuperhero(int superheroId){
        
        final String SELECT_SIGHTINGS_FOR_SUPERHERO = "SELECT * FROM sighting WHERE superheroId = ?";
        List<Sighting> sightings = jdbc.query(SELECT_SIGHTINGS_FOR_SUPERHERO, new SightingMapper(), superheroId);
        sightingDao.associateLocationsToSightings(sightings);
        return sightings;
        
    }
    
    @Override
    public List<Superhero> getAllSuperheroes() {
        
        final String GET_ALL_SUPERHEROES = "SELECT * FROM superhero";
        List<Superhero> superheroes = jdbc.query(GET_ALL_SUPERHEROES, new SuperheroMapper());
        associateSuperpowersAndSightings(superheroes);
        return superheroes;
        
    }

    public void associateSuperpowersAndSightings(List<Superhero> superheroes){
        
        for(Superhero superhero : superheroes){
            superhero.setSuperpowers(getSuperpowersForSuperhero(superhero.getId()));
            superhero.setSightings(getSightingsForSuperhero(superhero.getId()));
        }
        
    }
    
    @Override
    @Transactional
    public Superhero addSuperhero(Superhero superhero) {

        final String INSERT_SUPERHERO = "INSERT INTO superhero(isSuperhero, name, description) VALUES(?,?,?)";
        jdbc.update(INSERT_SUPERHERO,
                superhero.isIsHero(),
                superhero.getName(),
                superhero.getDescription());
        
        int newId = jdbc.queryForObject("SELECT LAST_INSERT_ID()", Integer.class);
        superhero.setId(newId);
        insertSuperpower(superhero);
        insertSighting(superhero);
        return superhero;

    }

    private void insertSuperpower(Superhero superhero){
        
        final String INSERT_HERO_SUPERPOWER = "INSERT INTO hero_superpower(superheroId, superpowerId) VALUES (?,?)";
        for(Superpower superpower : superhero.getSuperpowers()){
            jdbc.update(INSERT_HERO_SUPERPOWER,
                    superhero.getId(),
                    superpower.getId());
        }
        
    }
    
    private void insertSighting(Superhero superhero){
        
        final String INSERT_SIGHTING = "INSERT INTO sighting(superheroId, locationId, sightingDate) VALUES (?,?,?)";
        for(Sighting sighting : superhero.getSightings()){
            jdbc.update(INSERT_SIGHTING,
                    superhero.getId(),
                    sighting.getLocation().getId(),
                    sighting.getSightingDate());
            int newId = jdbc.queryForObject("SELECT LAST_INSERT_ID()", Integer.class);
            sighting.setId(newId);
        }
        
    }
    
    @Override
    @Transactional
    public Superhero updateSuperhero(Superhero superhero) {
        
        final String UPDATE_SUPERHERO = "UPDATE superhero SET isSuperhero = ?, name = ?, description = ? WHERE id = ?";
        jdbc.update(UPDATE_SUPERHERO,
                superhero.isIsHero(),
                superhero.getName(),
                superhero.getDescription(),
                superhero.getId());
        
        final String DELETE_HERO_SUPERPOWER = "DELETE FROM hero_superpower WHERE superheroId = ?";
        jdbc.update(DELETE_HERO_SUPERPOWER, superhero.getId());
        insertSuperpower(superhero);
        
        final String DELETE_SIGHTING = "DELETE FROM sighting WHERE superheroId = ?";
        jdbc.update(DELETE_SIGHTING, superhero.getId());
        insertSighting(superhero);
        
        return superhero;
        
    }

    @Override
    @Transactional
    public void deleteSuperheroById(int superheroId) {
        
        final String DELETE_HERO_SUPERPOWER = "DELETE FROM hero_superpower WHERE superheroId = ?";
        jdbc.update(DELETE_HERO_SUPERPOWER, superheroId);
        
        final String DELETE_HERO_ORGANIZATION = "DELETE FROM hero_organization WHERE superheroId = ?";
        jdbc.update(DELETE_HERO_ORGANIZATION, superheroId);
        
        final String DELETE_SIGHTING = "DELETE FROM sighting WHERE superheroId = ?";
        jdbc.update(DELETE_SIGHTING, superheroId);
        
        final String DELETE_SUPERHERO = "DELETE FROM superhero WHERE id = ?";
        jdbc.update(DELETE_SUPERHERO, superheroId);
        
    }

    @Override
    public List<Superhero> getSuperheroesForSuperpower(Superpower superpower) {
        
        final String SELECT_SUPERHEROES_FOR_SUPERPOWER = "SELECT * FROM superhero sh JOIN hero_superpower hs ON hs.superheroId = sh.id WHERE hs.superpowerId = ?";
        List<Superhero> superheroes = jdbc.query(SELECT_SUPERHEROES_FOR_SUPERPOWER, new SuperheroMapper(), superpower.getId());
        associateSuperpowersAndSightings(superheroes);
        return superheroes;
        
    }

    @Override
    public Superhero getSuperheroForSighting(Sighting sighting) {
        
        final String SELECT_SUPERHEROES_FOR_SIGHTING = "SELECT sh.* FROM superhero sh JOIN "
                + "sighting s ON s.superheroId = sh.id WHERE s.id = ?";
        Superhero superhero = jdbc.queryForObject(SELECT_SUPERHEROES_FOR_SIGHTING, new SuperheroMapper(), sighting.getId());
        superhero.setSuperpowers(getSuperpowersForSuperhero(superhero.getId()));
        superhero.setSightings(getSightingsForSuperhero(superhero.getId()));
        return superhero;
        
    }

    @Override
    public List<Superhero> getSuperHeroesForLocation(Location location) {
        
        final String SELECT_SUPERHEROES_FOR_LOCATION = "SELECT sh.* FROM superhero sh JOIN "
                + "sighting s on s.superheroId = sh.id WHERE s.locationId = ?";
        List<Superhero> superheroes = jdbc.query(SELECT_SUPERHEROES_FOR_LOCATION, new SuperheroMapper(), location.getId());
        associateSuperpowersAndSightings(superheroes);
        return superheroes;
        
    }

    public static final class SuperheroMapper implements RowMapper<Superhero>{

        @Override
        public Superhero mapRow(ResultSet rs, int rowNum) throws SQLException {
            Superhero hero = new Superhero();
            hero.setId(rs.getInt("id"));
            hero.setIsHero(rs.getBoolean("isSuperhero"));
            hero.setName(rs.getString("name"));
            hero.setDescription(rs.getString("description"));
            
            return hero;
        }
        
    }
    
}
