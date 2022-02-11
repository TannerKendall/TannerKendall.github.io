/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tk.superherosightingsdatalayer.dao;

import com.tk.superherosightingsdatalayer.entities.Location;
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
public class LocationDaoDB implements LocationDao {

    @Autowired
    JdbcTemplate jdbc;
    
    @Override
    public Location getLocationById(int locationId) {
        
        try{
            final String GET_LOCATION_BY_ID = "SELECT * FROM location WHERE id = ?";
            return jdbc.queryForObject(GET_LOCATION_BY_ID, new LocationMapper(), locationId);
        }catch(DataAccessException ex){
            return null;
        }
        
    }

    @Override
    public List<Location> getAllLocations() {
        
        final String GET_ALL_LOCATIONS = "SELECT * FROM location";
        return jdbc.query(GET_ALL_LOCATIONS, new LocationMapper());
        
    }

    @Override
    @Transactional
    public Location addLocation(Location location) {

        final String INSERT_LOCATION = "INSERT INTO location(name, latitude, longitude, description, addressInfo) VALUES(?,?,?,?,?)";
        jdbc.update(INSERT_LOCATION,
                location.getName(),
                location.getLatitude(),
                location.getLongitude(),
                location.getDescription(),
                location.getAddressInfo());
        
        int newId = jdbc.queryForObject("SELECT LAST_INSERT_ID()", Integer.class);
        location.setId(newId);
        return location;

    }

    @Override
    @Transactional
    public void updateLocation(Location location) {
        
        final String UPDATE_LOCATION = "UPDATE location SET name = ?, latitude = ?, longitude = ?, description = ?, addressInfo = ?"
                + " WHERE id = ?";
        jdbc.update(UPDATE_LOCATION,
                location.getName(),
                location.getLatitude(),
                location.getLongitude(),
                location.getDescription(),
                location.getAddressInfo(),
                location.getId());
        
    }

    @Override
    @Transactional
    public void deleteLocationById(int locationId) {
        
        final String DELETE_FROM_SIGHTING = "DELETE FROM sighting WHERE locationId = ?";
        jdbc.update(DELETE_FROM_SIGHTING, locationId);
        
        final String DELETE_FROM_LOCATION = "DELETE FROM location WHERE id = ?";
        jdbc.update(DELETE_FROM_LOCATION, locationId);
    }
    
    @Override
    public List<Location> getLocationsForSuperhero(Superhero superhero){
        
        final String SELECT_LOCATIONS_FOR_SUPERHERO = "SELECT l.* FROM location l "
                + "JOIN sighting s ON s.locationId = l.id WHERE s.superheroId = ?";
        List<Location> locationsForSuperhero = jdbc.query(SELECT_LOCATIONS_FOR_SUPERHERO, new LocationMapper(), superhero.getId());
        return locationsForSuperhero;
        
    }
    
    public static final class LocationMapper implements RowMapper<Location>{

        @Override
        public Location mapRow(ResultSet rs, int rowNum) throws SQLException {
            Location location = new Location();
            location.setId(rs.getInt("id"));
            location.setName(rs.getString("name"));
            location.setLatitude(rs.getDouble("latitude"));
            location.setLongitude(rs.getDouble("longitude"));
            location.setDescription(rs.getString("description"));
            location.setAddressInfo(rs.getString("addressInfo"));
            return location;
        }
        
    }
    
}
