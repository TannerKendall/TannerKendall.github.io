/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tk.superherosightingsdatalayer.dao;

import com.tk.superherosightingsdatalayer.dao.LocationDaoDB.LocationMapper;
import com.tk.superherosightingsdatalayer.entities.Location;
import com.tk.superherosightingsdatalayer.entities.Sighting;
import com.tk.superherosightingsdatalayer.entities.Superhero;
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
public class SightingDaoDB implements SightingDao {

    @Autowired
    JdbcTemplate jdbc;
    
    @Autowired
    LocationDao locationDao;
    
    @Override
    public Sighting getSightingById(int sightingId) {

        try{
            final String SELECT_SIGHTING_BY_ID = "SELECT * FROM sighting WHERE id = ?";
            Sighting sighting = jdbc.queryForObject(SELECT_SIGHTING_BY_ID, new SightingMapper(), sightingId);
            sighting.setLocation(getLocationForSighting(sightingId));
            return sighting;
        }catch(DataAccessException ex){
            return null;
        }

    }
    
    private Location getLocationForSighting(int sightingId){
        final String SELECT_LOCATION_FOR_SIGHTING = "SELECT l.* FROM location l JOIN sighting s "
                + "ON s.locationId = l.id WHERE s.id = ?";
        return jdbc.queryForObject(SELECT_LOCATION_FOR_SIGHTING, new LocationMapper(), sightingId);
    }

    @Override
    public List<Sighting> getAllSightings() {

        final String GET_ALL_SIGHTINGS = "SELECT * FROM sighting";
        List<Sighting> sightings = jdbc.query(GET_ALL_SIGHTINGS, new SightingMapper());
        associateLocationsToSightings(sightings);
        return sightings;

    }

    @Override
    public void associateLocationsToSightings(List<Sighting> sightings){
        for(Sighting sighting : sightings){
            sighting.setLocation(getLocationForSighting(sighting.getId()));
        }
    }
    
    @Override
    @Transactional
    public Sighting addSighting(Sighting sighting) {
        
        final String INSERT_SIGHTING = "INSERT INTO sighting(superheroId, locationId, sightingDate) VALUES(?,?,?)";
        jdbc.update(INSERT_SIGHTING,
                sighting.getSuperheroId(),
                sighting.getLocation().getId(),
                sighting.getSightingDate());
        
        int newId = jdbc.queryForObject("SELECT LAST_INSERT_ID()", Integer.class);
        sighting.setId(newId);
        return sighting;
    }

    @Override
    @Transactional
    public void updateSighting(Sighting sighting) {
        
        final String UPDATE_SIGHTING = "UPDATE sighting SET superheroId = ?, locationId = ?, sightingDate = ? WHERE id = ?";
        jdbc.update(UPDATE_SIGHTING,
                sighting.getSuperheroId(),
                sighting.getLocation().getId(),
                sighting.getSightingDate(),
                sighting.getId());
        
    }

    @Override
    @Transactional
    public void deleteSightingById(int sightingId) {
        
        final String DELETE_SIGHTING = "DELETE FROM sighting WHERE id = ?";
        jdbc.update(DELETE_SIGHTING, sightingId);
        
    }

    @Override
    public List<Sighting> getSightingsForLocation(Location location) {
        final String SELECT_SIGHTINGS_FOR_LOCATION = "SELECT * FROM sighting WHERE locationId = ?";
        List<Sighting> sightings = jdbc.query(SELECT_SIGHTINGS_FOR_LOCATION, new SightingMapper(), location.getId());
        associateLocationsToSightings(sightings);
        return sightings;
    }
    
    @Override
    public List<Sighting> getSightingsForSuperhero(Superhero superhero) {
        final String SELECT_SIGHTINGS_FOR_SUPERHERO = "SELECT * FROM sighting WHERE superheroId = ?";
        List<Sighting> sightings = jdbc.query(SELECT_SIGHTINGS_FOR_SUPERHERO, new SightingMapper(), superhero.getId());
        associateLocationsToSightings(sightings);
        return sightings;
    }
    
    @Override
    public List<Sighting> getMostRecentSightings() {
        try {
            final String SELECT_SIGHTINGS_BY_DATE = "SELECT * FROM sighting ORDER BY sightingDate DESC LIMIT 10";
            List<Sighting> sightings = jdbc.query(SELECT_SIGHTINGS_BY_DATE, new SightingMapper());
            associateLocationsToSightings(sightings);
            return sightings;
        } catch (DataAccessException ex) {
            return null;
        }
    }
    
    public static final class SightingMapper implements RowMapper<Sighting>{

        @Override
        public Sighting mapRow(ResultSet rs, int rowNum) throws SQLException {
            Sighting sighting = new Sighting();
            sighting.setId(rs.getInt("id"));
            sighting.setSuperheroId(rs.getInt("superheroId"));
            //sighting.setLocation(locationDao.getLocationById(locationId));
            sighting.setSightingDate(rs.getDate("sightingDate").toLocalDate());
            return sighting;
        }
        
    }
    
}
